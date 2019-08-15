package testplatcorp.resources;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import testplatcorp.data.domains.User;
import testplatcorp.repositories.UserRepository;
import testplatcorp.security.AccountCredentialsDTO;
import testplatcorp.security.jwt.JwtTokenProvider;

@Api(tags = "AuthenticationEndpoint") 
@RestController
@RequestMapping(value="/api/cliente/auth")
public class AuthResource {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private UserRepository repository;
	
	@ApiOperation(value="Autentica um usuário e retorna o token")
	@SuppressWarnings("rawtypes")
	@PostMapping(value="/signin", produces= {"application/json"}, consumes={"application/json"})
	public ResponseEntity signin(@RequestBody AccountCredentialsDTO credentials) {
		
		try {
			String username = credentials.getUsername();
			String pasword = credentials.getPassword();
			
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, pasword));
			
			User user = repository.findByUsername(username);
			
			String token = "";
			
			if (user != null) {
				token = tokenProvider.createToken(username, user.getRoles());
			} else {
				throw new UsernameNotFoundException("Username " + username + " not found!");
			}
			
			Map<Object, Object> model = new HashMap<>();
			model.put("username", username);
			model.put("token", token);
			
			return ResponseEntity.ok(model);
			
		} catch (AuthenticationException e) {
			throw new BadCredentialsException("Invalid username/password supplied!");
		}
	}
	
	
	
	
}
