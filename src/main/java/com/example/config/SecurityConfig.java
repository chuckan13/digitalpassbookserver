// package com.example.config;
// //Lots of import here

// @Configuration
// @EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true)
// public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//     @Autowired
//     private BCryptPasswordEncoder bCryptPasswordEncoder;

//     @Autowired
//     private DataSource dataSource;



//     @Value("${spring.queries.users-query}")
//     private String usersQuery;

//     @Value("${spring.queries.roles-query}")
//     private String rolesQuery;

//     @Override
//     protected void configure(AuthenticationManagerBuilder auth)
//             throws Exception {
//         auth.
//                 jdbcAuthentication()
//                 .usersByUsernameQuery(usersQuery)
//                 .authoritiesByUsernameQuery(rolesQuery)
//                 .dataSource(dataSource)
//                 .passwordEncoder(bCryptPasswordEncoder);
//     }

//     @Override
//     protected void configure(HttpSecurity http) throws Exception {

//         http.
//                 authorizeRequests()
//                 .antMatchers("/").permitAll()
//                 .antMatchers("/login").permitAll()
//                 .antMatchers("/registration").permitAll()
//                 .antMatchers("/admin/**").hasAuthority("ADMIN").anyRequest()
//                 .authenticated().and().csrf().disable().formLogin()
//                 .loginPage("/login").failureUrl("/login?error=true")
//                 .defaultSuccessUrl("/admin/home")
//                 .usernameParameter("email")
//                 .passwordParameter("password")
//                 .and().logout()
//                 .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                 .logoutSuccessUrl("/").and().exceptionHandling()
//                 .accessDeniedPage("/access-denied");
//     }

//     @Override
//     public void configure(WebSecurity web) throws Exception {
//         web
//                 .ignoring()
//                 .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
//     }

// }