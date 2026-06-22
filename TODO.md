# TODO

- [x] Update JWT security handlers to return JSON `ErrorResponse` instead of plain `sendError`.
- [x] Ensure responses include: success=false, status, message, error, path, timestamp.
- [ ] Ensure UserController implements all user endpoints: reset-password, search users, and /me.
- [ ] Fix UserController change-password endpoint response message and method naming.
- [ ] Apply PhoneNumberUtils.normalize() during create/update/search/lookup consistently (likely in service/validator).
- [ ] Add tests or run manual endpoint checks for:
  - /api/v1/users/{id}/change-password
  - /api/v1/users/{id}/reset-password
  - /api/v1/users?search=&enabled=&page=&size
  - /api/v1/users/me

