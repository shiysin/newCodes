package algo.LeetCode;

/**
 *   26. 有序数组去重
 */
public class LeetCode26 {
    public static void main(String[] args) {


    }

    public int removeDuplicates(int[] nums) {
        if(nums.length <= 1){
            return nums.length;
        }
        int j = 0;
        for(int i=1; i<nums.length; i++){
            if(nums[i] != nums[j]){
                j++;
                nums[j] = nums[i];
            }
        }
        return j+1;
    }
}
