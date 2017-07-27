delete from ems_user_detail where USER_ID in (1,2);

insert into ems_user_detail (USER_ID,USER_NAME,`PASSWORD`,EMAIL_ID,MOBILE_NUMBER,IS_ACTIVE,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE) values(1, 'user1', 'password123', 'test@gamil.com', '1212121212', '1',  'ADMIN', CURDATE(), NULL, NULL);
insert into ems_user_detail (USER_ID,USER_NAME,`PASSWORD`,EMAIL_ID,MOBILE_NUMBER,IS_ACTIVE,CREATED_BY,CREATED_DATE,UPDATED_BY,UPDATED_DATE) values(2, 'user2', 'password123', 'test@gamil.com', '1212121212', '1',  'ADMIN', CURDATE(), NULL, NULL);
