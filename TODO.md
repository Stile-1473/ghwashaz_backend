# TODO

- [ ] Update JWT security handlers to return JSON `ErrorResponse` instead of plain `sendError`.
  - File: src/main/java/Ascenso/sytem/security/handler/JwtAuthenticationEntryPoint.java (401)
  - File: src/main/java/Ascenso/sytem/security/handler/JwtAccessDeniedHandler.java (403)
- [ ] Ensure responses include: success=false, status, message, error, path, timestamp.
- [ ] (Optional) Adjust JwtAuthenticationFilter to throw/handle invalid tokens consistently.
- [ ] Run app / test endpoint with valid & invalid tokens; verify Postman shows JSON body.

