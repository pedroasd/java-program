package com.pedro.jdbc;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class SocialNetwork {

    private record User(Long id, String name, String surname, LocalDate birthDate) {}

    private record Friendship(Long userId1, Long userId2) {}

    private record Post( Long id,  Long userId, String text, LocalDateTime timestamp) {}

    private record Like(Long postId, Long userId,  LocalDateTime timestamp) {}

    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void initDb() {
        var users = createAndPopulateUsersTable();
        var usersWithMoreFriends = IntStream.range(1,10).mapToObj(i -> getRandomUserId(users)).collect(Collectors.toSet()).stream().toList();
        createAndPopulateFriendship(users, usersWithMoreFriends);
        var posts = createAndPopulatePostsTable(users, usersWithMoreFriends);
        creteAndPopulateLikesTable(posts, users);

        printTopUsers();
    }

    public void printTopUsers() {
        var topUsers = jdbcTemplate.query("SELECT u.name FROM users u " +
                        "WHERE (SELECT count(1) FROM friendships f WHERE f.userId1 = u.id) > ? " +
                        "AND (SELECT count(1) FROM posts p, likes l " +
                        "WHERE p.userId = u.id AND p.id = l.postId " +
                        "AND l.timestamp BETWEEN ? AND ?) > ?", (rs, rowNum) -> rs.getString("name"),
                100, LocalDateTime.parse("2025-03-01T00:00:00"), LocalDateTime.parse("2025-03-31T23:59:59"), 100);
        print("\n ****** " + topUsers.size() +" users with more than 100 friends, and 100 likes in March 2025 ****** \n");
        topUsers.forEach(System.out::println);
        print("");
    }

    private List<User> createAndPopulateUsersTable(){
        jdbcTemplate.execute("DROP TABLE users IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE users(" +
                "id BIGINT, name VARCHAR(255), surname VARCHAR(255), birthDate DATE," +
                "PRIMARY KEY (id))");

        List<User> users = IntStream.range(1,1100)
                .mapToObj(i -> new User((long) i, "Name " + i, "Surname " + i, LocalDate.now()))
                .toList();
        List<Object[]> params = users.stream().map(u -> new Object[] {u.id, u.name, u.surname, u.birthDate}).toList();
        jdbcTemplate.batchUpdate("INSERT INTO users(id, name, surname, birthDate) VALUES (?,?,?,?)", params);
        print(" Users table created and populated with " + users.size() + " records.");
        return users;
    }

    private void createAndPopulateFriendship(List<User> users, List<Long> usersWithMoreFriends){
        jdbcTemplate.execute("DROP TABLE friendships IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE friendships(" +
                "userId1 BIGINT, userId2 BIGINT, timestamp TIMESTAMP," +
                "PRIMARY KEY (userId1, userId2))");

        Set<Friendship> friendships = IntStream.range(1,70000)
                .mapToObj(i -> new Friendship(getRandomUserId(users), getRandomUserId(users)))
                .collect(Collectors.toSet());
        friendships.addAll(usersWithMoreFriends.stream().flatMap(userId -> IntStream.range(1,100 + userId.intValue())
                .mapToObj(i -> new Friendship(userId, getRandomUserId(users)))).toList());

        List<Object[]> params = friendships.stream().map(f -> new Object[] {f.userId1, f.userId2, LocalDateTime.now()}).toList();
        jdbcTemplate.batchUpdate("INSERT INTO friendships(userId1, userId2, timestamp) VALUES (?,?,?)", params);
        print(" Friendships table created and populated with " + friendships.size() + " records.");
    }

    private List<Post> createAndPopulatePostsTable(List<User> users, List<Long> usersWithMoreFriends){
        jdbcTemplate.execute("DROP TABLE posts IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE posts(" +
                "id BIGINT, userId BIGINT, text VARCHAR(255), timestamp TIMESTAMP," +
                "PRIMARY KEY (id)," +
                "CONSTRAINT ipfk FOREIGN KEY (userId) REFERENCES users (id) ON UPDATE CASCADE)");

        var mostLikedPosted = IntStream.range(0, 1000)
                .mapToObj(i -> new Post((long)i, usersWithMoreFriends.get(i % usersWithMoreFriends.size()), "Text " + i, LocalDateTime.now())).toList();

        var offset = mostLikedPosted.size();
        List<Post> posts = IntStream.range(offset, offset + 5000)
                .mapToObj(i -> new Post((long) i, getRandomUserId(users), "Text " + i, LocalDateTime.now()))
                .collect(Collectors.toList());
        posts.addAll(mostLikedPosted);
        List<Object[]> params = posts.stream().map(p -> new Object[] {p.id, p.userId, p.text, p.timestamp}).toList();
        jdbcTemplate.batchUpdate("INSERT INTO posts(id, userId, text, timestamp) VALUES (?,?,?,?)", params);
        print(" Posts table created and populated with " + posts.size() + " records.");
        return posts;
    }

    private void creteAndPopulateLikesTable(List<Post> posts, List<User> users) {
        jdbcTemplate.execute("DROP TABLE likes IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE likes(" +
                "postId BIGINT, userId BIGINT, timestamp TIMESTAMP," +
                "CONSTRAINT ilfk1 FOREIGN KEY (postId) REFERENCES posts (id) ON UPDATE CASCADE," +
                "CONSTRAINT ilfk2 FOREIGN KEY (userId) REFERENCES users (id) ON UPDATE CASCADE)");

        List<Like> likes = posts.stream().flatMap(post -> IntStream.range(1,500)
                .mapToObj(i -> new Like(post.id, getRandomUserId(users), i%2 == 0 ? LocalDateTime.parse("2025-03-02T17:19:33") : LocalDateTime.now()))).toList();

        List<Object[]> params = likes.stream().map(l -> new Object[] {l.postId, l.userId, l.timestamp}).toList();
        jdbcTemplate.batchUpdate("INSERT INTO likes(postId, userId, timestamp) VALUES (?,?,?)", params);
        print(" Likes table created and populated with " + likes.size() + " records.");
    }

    private Long getRandomUserId(List<User> users){
        var random = new Random();
        return users.get(random.nextInt(0, users.size())).id;
    }

    private void print(String string){
        System.out.println(string);
    }

}
