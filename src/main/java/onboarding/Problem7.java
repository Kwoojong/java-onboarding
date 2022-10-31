package onboarding;

import java.util.*;
import java.util.stream.Collectors;

public class Problem7 {
    public static List<String> solution(String user, List<List<String>> friends, List<String> visitors) {

        return validation(user, friends, visitors)
                ? List.of()
                : recommendation(user, friends, visitors);
    }

    private static boolean validation(String user, List<List<String>> friends, List<String> visitors) {
        if (userValidation(user))
            return true;
        else if (friendsValidation(friends))
            return true;
        else if (visitorsValidation(visitors))
            return true;
        return false;
    }

    private static boolean userValidation(String user) {
        return user.length() < 1 || user.length() > 30 || isNotLowerCaseAlphabet(user);
    }

    private static boolean friendsValidation(List<List<String>> friends) {
        if (friends.size() < 1 || friends.size() > 10000)
            return true;

        for (List<String> friend : friends) {
            String friendA = friend.get(0);
            String friendB = friend.get(1);
            if (friendA.length() < 1 || friendA.length() > 30 || friendB.length() < 1 || friendB.length() > 30)
                return true;
            if (isNotLowerCaseAlphabet(friendA) || isNotLowerCaseAlphabet(friendB))
                return true;
        }
        return false;
    }

    private static boolean visitorsValidation(List<String> visitors) {
        for (String visitor : visitors) {
            if (isNotLowerCaseAlphabet(visitor))
                return true;
        }
        return false;
    }

    private static boolean isNotLowerCaseAlphabet (String id) {
        return id.matches("^[^a-z]+$");
    }

    private static List<String> recommendation(String user, List<List<String>> friends, List<String> visitors) {

        Map<String, List<String>> collect = visitors.stream()
                .collect(Collectors.groupingBy(visitor -> visitor));

        Map<String, Integer> visitorsScore = new HashMap<>();
        for (String s : collect.keySet()) {
            visitorsScore.put(s, collect.get(s).size());
        }

        List<String> userFriend = new ArrayList<>();
        for (List<String> friend : friends) {
            if (friend.get(0).equals(user))
                userFriend.add(friend.get(1));
            if (friend.get(1).equals(user))
                userFriend.add(friend.get(0));
        }

        Map<String, Integer> friendsScore = new HashMap<>();

        for (List<String> friend : friends) {
            for (String userFriendTmp : userFriend) {
                if (friend.get(0).equals(userFriendTmp) && !friendsScore.containsKey(friend.get(1)) && !friend.get(1).equals(user))
                    friendsScore.put(friend.get(1), 10);
                else if (friend.get(0).equals(userFriendTmp) && friendsScore.containsKey(friend.get(1)) && !friend.get(1).equals(user))
                    friendsScore.put(friend.get(1), friendsScore.get(friend.get(1)) + 10);

                if (friend.get(1).equals(userFriendTmp) && !friendsScore.containsKey(friend.get(0)) && !friend.get(0).equals(user))
                    friendsScore.put(friend.get(0), 10);
                else if (friend.get(1).equals(userFriendTmp) && friendsScore.containsKey(friend.get(0)) && !friend.get(0).equals(user))
                    friendsScore.put(friend.get(0), friendsScore.get(friend.get(0)) + 10);
            }
        }

        visitorsScore.forEach((key, value) -> friendsScore.merge(key, value, Integer::sum));

        userFriend.forEach(friendsScore::remove);

        List<String> result = new ArrayList<>();
        friendsScore.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> result.add(x.getKey()));
        return result;
    }
}
