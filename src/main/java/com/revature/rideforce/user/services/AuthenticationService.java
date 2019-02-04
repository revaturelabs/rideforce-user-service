package com.revature.rideforce.user.services;
import java.text.ParseException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.SignUpRequest;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.proc.BadJOSEException;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import com.nimbusds.jwt.proc.JWTProcessor;
import com.revature.rideforce.user.beans.RegistrationToken;
import com.revature.rideforce.user.beans.User;
import com.revature.rideforce.user.beans.UserRegistration;
import com.revature.rideforce.user.config.CognitoConfig;
import com.revature.rideforce.user.config.JWKConfig;
import com.revature.rideforce.user.exceptions.EmptyPasswordException;
import com.revature.rideforce.user.exceptions.EntityConflictException;
import com.revature.rideforce.user.exceptions.InvalidRegistrationKeyException;
import com.revature.rideforce.user.exceptions.PasswordRequirementsException;
import com.revature.rideforce.user.exceptions.PermissionDeniedException;
import com.revature.rideforce.user.repository.OfficeRepository;
import com.revature.rideforce.user.repository.UserRepository;

/**
 * The service used to handle authentication, that is, logging in, creating new
 * users, getting information about the current user.
 */
@Service
public class AuthenticationService {
	@Value("${jwt.registration.ttl}")
	private Integer registrationTokenTTL;
	
	@Autowired
	private Logger log;
	
	@Autowired
	OfficeRepository or;
	
	@Autowired
	private CognitoConfig cc;
	
	@Autowired
	private UserService us;
	
	@Autowired
	private UserRepository ur;
	
	@Autowired
	AWSCognitoIdentityProvider cognito;

	private JWSSigner registrationTokenSigner;
	private JWSHeader registrationTokenHeader;
	private JWTProcessor<SecurityContext> jwtProcessor;
	
	@Autowired
	public AuthenticationService(JWKConfig jc, ImmutableJWKSet<SecurityContext> jwkSet) throws JOSEException {
		// Create the registration token header
		this.registrationTokenHeader = new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(jc.getId()).build();
		// Create the registration token signer
		this.registrationTokenSigner = new RSASSASigner(((RSAKey)jwkSet.getJWKSet().getKeyByKeyId(jc.getId())).toPrivateKey());
		// Create the token processor
		ConfigurableJWTProcessor<SecurityContext> jwtProcessor = new DefaultJWTProcessor<>();
		jwtProcessor.setJWSKeySelector(new JWSVerificationKeySelector<>(JWSAlgorithm.RS256, jwkSet));
		this.jwtProcessor = jwtProcessor;
	}

	/**
	 * Registers a new user with the given information.
	 * 
	 * @param info the registration information for the new user
	 * @return the user that was created
	 * @throws InvalidRegistrationKeyException if the provided registration key was
	 *                                         invalid
	 * @throws EntityConflictException         if the given email is already used by
	 *                                         another user
	 * @throws PermissionDeniedException       if the currently logged-in user does
	 *                                         not have permission to create the
	 *                                         desired user (e.g. if an
	 *                                         unauthenticated user attempts to
	 *                                         create an admin)
	 * @throws EmptyPasswordException          Password in the {@linkplain UserRegistration} must be non empty
	 * @throws PasswordRequirementsException   if the password entered does not
	 * 										   meet the requirements specified
	 */
	public User register(UserRegistration ur) throws InvalidRegistrationKeyException, EntityConflictException, PermissionDeniedException, EmptyPasswordException, PasswordRequirementsException {
		// Check the registration token
		RegistrationToken registrationToken = validateRegistrationToken(ur.getRegistrationToken());
		if(registrationToken != null) {
			ur.getUser().setOffice(registrationToken.getOffice());
			ur.getUser().setBatchEnd(registrationToken.getBatchEndDate());
		} else {
			throw new InvalidRegistrationKeyException();
		}
		
		// Sign the user up with Cognito
		cognito.signUp(new SignUpRequest()
				.withClientId(cc.getClientId())
				.withUsername(ur.getUser().getEmail().toLowerCase())
				.withPassword(ur.getUser().getPassword()));
		
		// Add the user to our database
		return us.add(ur.getUser());
	}

	/**
	 * Gets the {@link User} object corresponding to the currently authenticated
	 * user.
	 * 
	 * @return the currently authenticated user, or {@code null} if no user is
	 *         logged in (according to the authentication information stored in the
	 *         {@link SecurityContextHolder}
	 */
	public User getCurrentUser() {
		log.info("Getting current user from Authentication");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		log.debug("Authentication value: {}", auth);

		if (auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof User)) {
			log.debug("User is null"); 
			return null;
		}
    
		log.debug("User authenticated successfully");
		return (User) auth.getPrincipal();
	}
	
	public String createRegistrationToken(RegistrationToken rt) throws JOSEException {
		// Prepare the JWT claims
		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
		    .subject("registration")
		    .issuer("rideforce")
		    .claim("oid", rt.getOffice().getId())
		    .claim("bed", rt.getBatchEndDate())
		    .issueTime(Date.from(Instant.now()))
		    .expirationTime(Date.from(Instant.now().plus(Duration.ofHours(registrationTokenTTL))))
		    .build();
		
		// Create the JWT
		SignedJWT signedJWT = new SignedJWT(registrationTokenHeader, claimsSet);

		// Sign the JWT
		signedJWT.sign(registrationTokenSigner);
		
		// Return the signed JWT
		return signedJWT.serialize();
	}
	
	public JWTClaimsSet processToken(String token) {
		try {
			return jwtProcessor.process(token, null);
		} catch (ParseException | BadJOSEException | JOSEException e) {
			return null;
		}
	}
	
	public RegistrationToken validateRegistrationToken(String token) {
		return Optional.ofNullable(token)
				.map(this::processToken)
				.map(c -> new RegistrationToken(or.findById(Integer.parseInt(c.getClaim("oid").toString())), new Date(Long.parseLong(c.getClaim("bed").toString()) * 1000L)))
				.orElse(null);
	}
	
	/**
	 * Converts the given JWT into an authentication object that can be stored as the
	 * authentication principal in Spring's SecurityContext.
	 * 
	 * @param token the JWT to convert.
	 * @return the authentication object, or null if the given JWT was invalid.
	 */
	public Authentication authenticate(String token) {
		return Optional.ofNullable(token)
				// Verify the token
				.map(this::processToken)
				// Extract the email from the token
				.map(t -> t.getClaim("email"))
				// Convert the email claim to a string
				.map(Object::toString)
				// Find a user by the email
				.map(ur::findByEmail)
				// Create an authentication object from the user
				.map(u -> new UsernamePasswordAuthenticationToken(u, "", u.getAuthorities()))
				// Return null if anything in this chain is null
				.orElse(null);
	}
}
