CREATE TABLE ServiceUser
(
    id         SERIAL primary key not null,
    firstName  text               not null,
    lastName   text               not null,
    userName   text               not null,
    telegramId integer unique     not null
);

CREATE TABLE FollowedArticle
(
    id            SERIAL primary key not null,
    userId        BIGINT             not null references ServiceUser (id),
    searchRequest text               not null,
    since timestamp not null default now(),
    chatId BIGINT not null
);
