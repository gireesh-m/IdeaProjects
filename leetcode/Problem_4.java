public class Problem_4 {
// 4. Median of Two Sorted Arrays

    private class SolutionRange {
        public int l,r;
        public SolutionRange(int len) {
            this.l = 0;
            this.r = len-1;
        }
        public int getSize() {
            return this.r - this.l;
        }
        public int getMedian() {
            return (this.r - this.l)/2;
        }
        @Override
        public String toString() {
            return String.format("l: %d r: %d mid: %d", this.l, this.r, this.getMedian());
        }
    }


    private int findIndexSortedArrays(int[] nums1, int[] nums2, int index) {
        if (nums1.length < nums2.length)
            return findIndexSortedArrays(nums2, nums1, index);
        SolutionRange nums1Range = new SolutionRange(nums1.length);

        while (nums1Range.getSize() > 1) {
            // find median of nums1
            int mid1 = nums1Range.getMedian();
            // assume mid1 is value we are looking for. This means that
            // mid1 is the value at index the combined sorted array.
            // we also know that mid1 + mid2 = index - 1 if mid1 is truly the
            // correct solution so mid2 = index - mid1 - 1
            int mid2 = index - mid1 - 1;
            // if mid2 is negative, then we know that mid1 is too large
            // If nums1[mid1] > nums2[mid2]: (asterisks represents mid1 and mid2)
            // nums1: [1,2,4,*10*]
            // nums2: [*3*,4,5]
            // no way any numbers of nums1 at
            // indices mid1+1 and up can be solutions. 
            if (nums1[mid1] > nums2[mid2]) {
                nums1Range.r = mid1;
            } else if (nums1[mid1] < nums2[mid2]) {
                // Otherwise: nums1[mid1] <= nums2[mid2]. Ex:
                // nums1: [1,*2*,4,10]
                // nums2: [3,*5*,6]
                // then we know that nums1[mid1] is not the solution
                // and all numbers at indices mid1 and below are not solutions
                nums1Range.l = mid1;
            } else {
                // nums1[mid1] == nums2[mid2]
                return nums1[mid1];
            }
        }
        if ((nums1.length + nums2.length) % 2 == 0) {
            int num1 = nums1[nums1Range.l];
            int num2 = nums2[index - nums1Range.l - 1];
            return Math.max(num1, num2);
        } else {
            int num1 = nums1[nums1Range.r];
            int num2 = nums2[index - nums1Range.r - 1];
            return Math.max(num1, num2);
        }
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int goalLen = (nums1.length+nums2.length)/2;
        System.out.println(goalLen);
        if ((nums1.length + nums2.length) % 2 == 1) {
            return findIndexSortedArrays(nums1, nums2, goalLen);
        } else {
            int at = findIndexSortedArrays(nums1, nums2, goalLen);
            int below = findIndexSortedArrays(nums1, nums2, goalLen-1);
            System.out.println(at);
            System.out.println(below);
            return (at + below)/2.0;
        }
    }

    public static void main(String[] args) {
        Problem_4 sol = new Problem_4();
        int[] nums1 = new int[]{1,2};
        int[] nums2 = new int[]{3,4};
        double ans = sol.findMedianSortedArrays(nums1, nums2);
        // double ans = sol.findIndexSortedArrays(nums1, nums2, (nums1.length + nums2.length)/2);
        System.out.println(ans);
    }
}
