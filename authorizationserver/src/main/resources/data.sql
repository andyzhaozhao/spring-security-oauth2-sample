-- 注册client-for-server客户端，client_secret使用BCryptPasswordEncoder加密后的字符串
insert into oauth_client_details
( `client_id`, `resource_ids`, `client_secret`, `scope`, `authorized_grant_types`,
`web_server_redirect_uri`, `authorities`, `access_token_validity`, `refresh_token_validity`,
 `additional_information`, `autoapprove`)
values
( 'client-for-server', 'authorizationserver', '$2a$10$UmAZtVnLzegIdP7BfBEHz.f8bMqRd3ZOlFeUKBanAOGCO8iIFQaE.',
'profile,email,phone', 'authorization_code', 'http://localhost:8080/login/oauth2/code/authorizationserver',
 'ROLE_CLIENT', '7200', '72000', '{}', 'profile');