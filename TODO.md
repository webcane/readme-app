Update
=============
- update java to v.17
- update spring boot to v.3.1.4
- migrate from jjwt to nimbus 9.24.4
- javax.validation.constraints -> jakarta.validation.constraints
- javax.servlet -> jakarta.servlet
- WebSecurityConfigurerAdapter
- HttpTraceFilter


Authorization
=============
- GET /oauth2/authorization/github
- Redirecting to GET https://github.com/login/oauth/authorize?response_type=code&client_id=b4d290692c6fbed46...&scope=user:email%20read:user&state=lbKyoL4a...&redirect_uri=http://localhost:8080/login/oauth2/code/github
- Redirecting to GET /login/oauth2/code/github?code=010d24659442ddb3e039&state=lbKyoL4a..
- Redirecting to POST https://github.com/login/oauth/access_token
- Redirecting to GET https://api.github.com/user
- Redirecting to GET /?token=eyJhb.GciOi.JIUzI...


// HttpSessionOAuth2AuthorizationRequestRepository
// default SavedRequestAwareAuthenticationSuccessHandler
// default SimpleUrlAuthenticationFailureHandler