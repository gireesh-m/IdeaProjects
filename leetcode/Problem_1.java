import java.util.HashMap;
import java.util.Map;

public class Problem_1 {
    // 1. Two Sum
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int val = nums[i];
            Integer matchingIndex;
            if ((matchingIndex = map.get(target-val)) != null && matchingIndex != i) {
                return new int[]{i, matchingIndex};
            } else {
                map.put(val, i);
            }
        }
        return new int[]{0,0};
    }
}
