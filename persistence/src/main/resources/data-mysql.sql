-- Users

delete from user where id <= 6;

insert into user (id,password,user_name, email, role)
values (6,'$2a$08$rWw15tM4D8JGNkoSnosvUOWktluv1CnxCZfJslvHWaiFSaSOpnYZS','boisterous', 'neuban35@gmail.com', 'USER');

insert into user (id,password,user_name, email, role)
values (5,'$2a$08$2/f6i7QzmdvM0wVDghFdkOUPtUA66SSaOGCNZj8uEczUodB49ZpIS','traumatic', 'neuban34@gmail.com', 'USER');

insert into user (id,password,user_name, email, role)
values (4,'$2a$08$rxpMCbm7zfyQR5GVaah1duKOyoGDPCgsN1ruXkWgUagbfyyXl7J/a','chopchop', 'neuban33@gmail.com', 'USER');

insert into user (id,password,user_name, email, role)
values (3,'$2a$08$5MBR1BRsenDZSKyQoofETubRmvH3GIxgDs3LTZlCFMjfftFn3HM9K','krisztmas', 'neuban32@gmail.com', 'USER');

insert into user (id,password,user_name, email, role)
values (2,'$2a$08$P2LGQAUIk6TjQcLE533Ec.5EFTwUPJOmt1LsDKmS9PbJxW6tKGz8u','pivanyi', 'neuban31@gmail.com', 'USER');

insert into user (id,password,user_name, email, role)
values (1,'$2a$08$0E.G3Tt9BsDtapmmc1pgruh4myKCy6ySEi//ptScWjRmres1U0txi','falatka', 'neuban3@gmail.com', 'USER');

-- Boards

delete from board where id <= 2;

insert into board (id,title)
values (1,'Test board 1');

insert into board (id,title)
values (2,'Test board 2');

-- User-Board

delete from user_board_relation_table where user_id = 1;

insert into user_board_relation_table (user_id,board_id)
values (1,1);

insert into user_board_relation_table (user_id,board_id)
values (1,2);

-- Columns

delete from board_column where id <= 4;

insert into board_column (id,title,board_id)
values (1,'TODO',1);

insert into board_column (id,title,board_id)
values (2,'IN DEV',1);

insert into board_column (id,title,board_id)
values (3,'QA',1);

insert into board_column (id,title,board_id)
values (4,'DONE',1);

-- Cards

delete from card where id <= 8;

insert into card (id,description,title,column_id)
values (1,'Sample description for task 1','Task 1',1);

insert into card (id,description,title,column_id)
values (2,'Sample description for task 2','Task 2',1);

insert into card (id,description,title,column_id)
values (3,'Sample description for task 3','Task 3',2);

insert into card (id,description,title,column_id)
values (4,'Sample description for task 4','Task 4',3);

insert into card (id,description,title,column_id)
values (5,'Sample description for task 5','Task 5',3);

insert into card (id,description,title,column_id)
values (6,'Sample description for task 6','Task 6',4);

insert into card (id,description,title,column_id)
values (7,'Sample description for task 7','Task 7',4);

insert into card (id,description,title,column_id)
values (8,'Sample description for task 8','Task 8',4);

insert into comment (id,content,created_time,user_id,card_id)
values (1,"Some birthday content",'1993-12-03 12:30',1,1);

insert into comment (id,content,created_time,user_id,card_id)
values (2,"Some birthday content",'1994-08-20 12:30',2,1);

insert into comment (id,content,created_time,user_id,card_id)
values (3,"Some birthday content",'1994-08-19 06:30',3,1);

-- User-Card

delete from user_card_relation_table where user_id = 1;

insert into user_card_relation_table (card_id,user_id)
values (3,1);

insert into user_card_relation_table (card_id,user_id)
values (4,1);

insert into user_card_relation_table (card_id,user_id)
values (7,1);