package db;

import com.google.common.collect.Maps;

import model.Board;
import model.User;

import java.util.Collection;
import java.util.Map;

public class Database {
    private static Map<String, User> users = Maps.newHashMap();
	private static Map<Long, Board> boards = Maps.newHashMap();

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public static User findUserById(String userId) {
		return users.get(userId); //hashmap 때문에 key가 null이면 null 반환
    }

    public static Collection<User> findAllUser() {
        return users.values();
    }

	public static void removeAllUser() {
		users.clear();
	}

	public static void addBoard(Board board){
		boards.put(board.getBoardId(), board);
	}

	public static Board findBoardById(Long userId) {
		return boards.get(userId); //hashmap 때문에 key가 null이면 null 반환
	}

	public static Collection<Board> findAllBoard() {
		return boards.values();
	}

	public static void removeAllBoard() {
		boards.clear();
	}
}
