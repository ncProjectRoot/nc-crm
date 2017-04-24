DROP SCHEMA IF EXISTS public CASCADE;
commit;
CREATE SCHEMA public;
commit;

CREATE TABLE address 
    ( 
     id BIGSERIAL  NOT NULL , 
     latitude DOUBLE PRECISION  NOT NULL , 
     longitude DOUBLE PRECISION  NOT NULL , 
     region_id INTEGER  NOT NULL 
    ) 
;


CREATE INDEX address__IDX ON address 
    ( 
     latitude ASC , 
     longitude ASC 
    ) 
;

ALTER TABLE address 
    ADD CONSTRAINT address_PK PRIMARY KEY ( id ) ;


CREATE TABLE complaint 
    ( 
     id BIGSERIAL  NOT NULL , 
     message VARCHAR (400)  NOT NULL , 
     status_id INTEGER  NOT NULL , 
     "date" TIMESTAMP(0)  NOT NULL , 
     user_id INTEGER  NOT NULL , 
     pmg_id INTEGER  NOT NULL , 
     order_id INTEGER  NOT NULL 
    ) 
;



ALTER TABLE complaint 
    ADD CONSTRAINT complaint_PK PRIMARY KEY ( id ) ;


CREATE TABLE discount 
    ( 
     id BIGSERIAL  NOT NULL , 
     title VARCHAR (50)  NOT NULL , 
     percentage DOUBLE PRECISION  NOT NULL , 
     description VARCHAR (400)  NOT NULL , 
     date_start TIMESTAMP(0) , 
     date_finish TIMESTAMP(0) 
    ) 
;


CREATE INDEX discount__IDX ON discount 
    ( 
     title ASC 
    ) 
;

ALTER TABLE discount 
    ADD CONSTRAINT discount_PK PRIMARY KEY ( id ) ;


CREATE TABLE "group" 
    ( 
     id BIGSERIAL  NOT NULL , 
     name VARCHAR (50)  NOT NULL , 
     discount_id INTEGER  NOT NULL 
    ) 
;



ALTER TABLE "group" 
    ADD CONSTRAINT group_PK PRIMARY KEY ( id ) ;

ALTER TABLE "group" 
    ADD CONSTRAINT group__UN UNIQUE ( discount_id ) ;


CREATE TABLE history 
    ( 
     id BIGSERIAL  NOT NULL , 
     old_status_id INTEGER  NOT NULL , 
     date_change_status TIMESTAMP(0)  NOT NULL , 
     desc_change_status VARCHAR (100)  NOT NULL , 
     order_id INTEGER  NOT NULL , 
     complaint_id INTEGER  NOT NULL 
    ) 
;



ALTER TABLE history 
    ADD CONSTRAINT history_PK PRIMARY KEY ( id ) ;


CREATE TABLE "order" 
    ( 
     id BIGSERIAL  NOT NULL , 
     "date" TIMESTAMP(0)  NOT NULL , 
     actual_status_id INTEGER  NOT NULL , 
     user_id INTEGER  NOT NULL , 
     product_id INTEGER  NOT NULL , 
     csr_id INTEGER  NOT NULL 
    ) 
;




ALTER TABLE "order" 
    ADD CONSTRAINT order_PK PRIMARY KEY ( id ) ;

ALTER TABLE "order" 
    ADD CONSTRAINT order__UN UNIQUE ( "date" , user_id ) ;

ALTER TABLE "order" 
    ADD CONSTRAINT order__UNv1 UNIQUE ( csr_id ) ;


CREATE TABLE organization 
    ( 
     id BIGSERIAL  NOT NULL , 
     name VARCHAR (50)  NOT NULL 
    ) 
;



ALTER TABLE organization 
    ADD CONSTRAINT organization_PK PRIMARY KEY ( id ) ;


CREATE TABLE persistent_logins 
    ( 
     series character varying(64)  NOT NULL , 
     username character varying(64)  NOT NULL , 
     token character varying(64)  NOT NULL , 
     last_used timestamp without time zone  NOT NULL 
    ) 
;



ALTER TABLE persistent_logins 
    ADD CONSTRAINT persistent_logins_PK PRIMARY KEY ( series ) ;


CREATE TABLE product 
    ( 
     id BIGSERIAL  NOT NULL , 
     title VARCHAR (50)  NOT NULL , 
     default_price DOUBLE PRECISION , 
     status_id INTEGER  NOT NULL , 
     description VARCHAR (400) , 
     discount_id INTEGER  NOT NULL , 
     group_id INTEGER  NOT NULL 
    ) 
;


CREATE INDEX product__IDX ON product 
    ( 
     title ASC 
    ) 
;

ALTER TABLE product 
    ADD CONSTRAINT product_PK PRIMARY KEY ( id ) ;

ALTER TABLE product 
    ADD CONSTRAINT product__UN UNIQUE ( discount_id ) ;


CREATE TABLE region 
    ( 
     id BIGSERIAL  NOT NULL , 
     name VARCHAR (50)  NOT NULL , 
     discount_id INTEGER  NOT NULL 
    ) 
;



ALTER TABLE region 
    ADD CONSTRAINT region_PK PRIMARY KEY ( id ) ;

ALTER TABLE region 
    ADD CONSTRAINT region__UN UNIQUE ( discount_id ) ;


CREATE TABLE region_groups 
    ( 
     id BIGSERIAL  NOT NULL , 
     region_id INTEGER  NOT NULL , 
     group_id INTEGER  NOT NULL 
    ) 
;



ALTER TABLE region_groups 
    ADD CONSTRAINT region_groups_PK PRIMARY KEY ( id ) ;

ALTER TABLE region_groups 
    ADD CONSTRAINT region_groups__UN UNIQUE ( region_id , group_id ) ;


CREATE TABLE statuses 
    ( 
     id BIGSERIAL  NOT NULL , 
     name VARCHAR (50)  NOT NULL 
    ) 
;



ALTER TABLE statuses 
    ADD CONSTRAINT statuses_PK PRIMARY KEY ( id ) ;


CREATE TABLE "user" 
    ( 
     id BIGSERIAL  NOT NULL , 
     password VARCHAR (100)  NOT NULL , 
     first_name VARCHAR (50)  NOT NULL , 
     middle_name VARCHAR (50)  NOT NULL , 
     last_name VARCHAR (50) , 
     phone VARCHAR (20) , 
     email VARCHAR (320)  NOT NULL , 
     enable CHAR (1)  NOT NULL , 
     account_non_locked CHAR (1)  NOT NULL , 
     address_id INTEGER  NOT NULL , 
     user_role_id INTEGER  NOT NULL , 
     org_id INTEGER  NOT NULL , 
     my_org_id INTEGER  NOT NULL 
    ) 
;


CREATE INDEX user__IDX ON "user" 
    ( 
     first_name ASC , 
     middle_name ASC , 
     last_name ASC 
    ) 
;

ALTER TABLE "user" 
    ADD CONSTRAINT user_PK PRIMARY KEY ( id ) ;

ALTER TABLE "user" 
    ADD CONSTRAINT user__UN UNIQUE ( email ) ;


CREATE TABLE user_roles 
    ( 
     id BIGSERIAL  NOT NULL , 
     name VARCHAR (30)  NOT NULL 
    ) 
;



ALTER TABLE user_roles 
    ADD CONSTRAINT group_user_PK PRIMARY KEY ( id ) ;



--//////////////////////////////////////////////////////////////////////////////////

ALTER TABLE address 
    ADD CONSTRAINT address_region_FK FOREIGN KEY 
    ( 
     region_id
    ) 
    REFERENCES region 
    ( 
     id
    ) 
    ON DELETE CASCADE 
;


ALTER TABLE complaint 
    ADD CONSTRAINT complaint_order_FK FOREIGN KEY 
    ( 
     order_id
    ) 
    REFERENCES "order" 
    ( 
     id
    ) 
    ON DELETE CASCADE 
;


ALTER TABLE complaint 
    ADD CONSTRAINT complaint_statuses_FK FOREIGN KEY 
    ( 
     status_id
    ) 
    REFERENCES statuses 
    ( 
     id
    ) 
;


ALTER TABLE complaint 
    ADD CONSTRAINT complaint_user_FK FOREIGN KEY 
    ( 
     user_id
    ) 
    REFERENCES "user" 
    ( 
     id
    ) 
;


ALTER TABLE complaint 
    ADD CONSTRAINT complaint_user_FKv1 FOREIGN KEY 
    ( 
     pmg_id
    ) 
    REFERENCES "user" 
    ( 
     id
    ) 
;


ALTER TABLE "group" 
    ADD CONSTRAINT group_discount_FK FOREIGN KEY 
    ( 
     discount_id
    ) 
    REFERENCES discount 
    ( 
     id
    ) 
;


ALTER TABLE history 
    ADD CONSTRAINT history_complaint_FK FOREIGN KEY 
    ( 
     complaint_id
    ) 
    REFERENCES complaint 
    ( 
     id
    ) 
    ON DELETE CASCADE 
;


ALTER TABLE history 
    ADD CONSTRAINT history_order_FK FOREIGN KEY 
    ( 
     order_id
    ) 
    REFERENCES "order" 
    ( 
     id
    ) 
    ON DELETE CASCADE 
;


ALTER TABLE history 
    ADD CONSTRAINT history_statuses_FK FOREIGN KEY 
    ( 
     old_status_id
    ) 
    REFERENCES statuses 
    ( 
     id
    ) 
    ON DELETE CASCADE 
;


ALTER TABLE "order" 
    ADD CONSTRAINT order_product_FK FOREIGN KEY 
    ( 
     product_id
    ) 
    REFERENCES product 
    ( 
     id
    ) 
;


ALTER TABLE "order" 
    ADD CONSTRAINT order_statuses_FK FOREIGN KEY 
    ( 
     actual_status_id
    ) 
    REFERENCES statuses 
    ( 
     id
    ) 
;


ALTER TABLE "order" 
    ADD CONSTRAINT order_user_FK FOREIGN KEY 
    ( 
     user_id
    ) 
    REFERENCES "user" 
    ( 
     id
    ) 
;


ALTER TABLE "order" 
    ADD CONSTRAINT order_user_FKv1 FOREIGN KEY 
    ( 
     csr_id
    ) 
    REFERENCES "user" 
    ( 
     id
    ) 
;


ALTER TABLE product 
    ADD CONSTRAINT product_discount_FK FOREIGN KEY 
    ( 
     discount_id
    ) 
    REFERENCES discount 
    ( 
     id
    ) 
;


ALTER TABLE product 
    ADD CONSTRAINT product_group_FK FOREIGN KEY 
    ( 
     group_id
    ) 
    REFERENCES "group" 
    ( 
     id
    ) 
    ON DELETE CASCADE 
;


ALTER TABLE product 
    ADD CONSTRAINT product_statuses_FK FOREIGN KEY 
    ( 
     status_id
    ) 
    REFERENCES statuses 
    ( 
     id
    ) 
;


ALTER TABLE region 
    ADD CONSTRAINT region_discount_FK FOREIGN KEY 
    ( 
     discount_id
    ) 
    REFERENCES discount 
    ( 
     id
    ) 
;


ALTER TABLE region_groups 
    ADD CONSTRAINT region_groups_group_FK FOREIGN KEY 
    ( 
     group_id
    ) 
    REFERENCES "group" 
    ( 
     id
    ) 
    ON DELETE CASCADE 
;


ALTER TABLE region_groups 
    ADD CONSTRAINT region_groups_region_FK FOREIGN KEY 
    ( 
     region_id
    ) 
    REFERENCES region 
    ( 
     id
    ) 
    ON DELETE CASCADE 
;


ALTER TABLE "user" 
    ADD CONSTRAINT user_address_FK FOREIGN KEY 
    ( 
     address_id
    ) 
    REFERENCES address 
    ( 
     id
    ) 
;


ALTER TABLE "user" 
    ADD CONSTRAINT user_organization_FK FOREIGN KEY 
    ( 
     org_id
    ) 
    REFERENCES organization 
    ( 
     id
    ) 
;


ALTER TABLE "user" 
    ADD CONSTRAINT user_organization_FKv1 FOREIGN KEY 
    ( 
     my_org_id
    ) 
    REFERENCES organization 
    ( 
     id
    ) 
;


ALTER TABLE "user" 
    ADD CONSTRAINT user_region_FKv1 FOREIGN KEY 
    ( 
     user_role_id
    ) 
    REFERENCES user_roles 
    ( 
     id
    ) 
;

commit;

