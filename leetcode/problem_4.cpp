#include <iostream>
#include <vector>

using namespace std;

class Problem_4 {
private:
    pair<int,double> verifySplit(vector<int>& nums1, vector<int>& nums2, int i1, int i2) {
        int num1Lo = i1 >= 0 ? nums1[i1] : INT_MIN;
        int num1Hi = i1+1 < nums1.size() ? nums1[i1+1] : INT_MAX;
        int num2Lo = i2 >= 0 ? nums2[i2] : INT_MIN;
        int num2Hi = i2+1 < nums2.size() ? nums2[i2+1] : INT_MAX;
        if (num1Hi < num2Lo) {
            // our split looks something like this:
            // nums1: 1   2   3  /  4   5
            // nums2: 100 / 101  102  103
            // therefore our i1 needs to be greater
            return make_pair(-1,0);
        } else if (num1Lo > num2Hi) {
            // our split looks something like this:
            // nums1: 100 101 102 / 103 104
            // nums2: 2  /  3  4  5
            // therefore our i1 needs to be smaller
            return make_pair(1,0);
        } else {
            // we found a valid split!
            // now we get the median value
            if ((nums1.size() + nums2.size()) % 2 == 1) {
                // if the combined array is odd length, then we take the min
                // of both the hi values because our mid value is computed
                // via rounding DOWN, so the lower partition we are computing
                // will ALWAYS be less than or equal to the size of the higher
                // partition
                return make_pair(0, min(num1Hi, num2Hi));
            } else {
                // if the combined array is even length, then we average the
                // larger of the lo's and smaller of the hi's
                int leftPartitionValue = max(num1Lo, num2Lo);
                int rightPartitionValue = min(num1Hi, num2Hi);
                return make_pair(0, (leftPartitionValue+rightPartitionValue)/2.0);
            }
        }
    }
public:
    double findMedianSortedArrays(vector<int>& nums1, vector<int>& nums2) {
        if (nums1.size() < nums2.size()) {
            return findMedianSortedArrays(nums2, nums1);
        } else if (nums2.size() == 0) {
            if (nums1.size() == 0) {
                return 0;
            } else {
                int s = nums1.size();
                return (nums1[s/2] + nums1[(s-1)/2])/2.0;
            }
        } else if (nums1.size() == 1 && nums2.size() == 1) {
            return (nums1[0] + nums2[0])/2.0;
        }
        // lo is lower bound inclusive, hi is upper bound inclusive
        // k is desired *length* of our left parition. 
        int lo = 0, hi = nums1.size() - 1, k = (nums1.size() + nums2.size())/2;
        bool loChanged = false;
        while (lo < hi) {
            // mid is the INDEX at which we will make an INCLUSIVE partition
            int mid = (hi+lo)/2;
            // leftOver is the remaining LENGTH we need from nums2
            int leftOver = k - mid - 1;
            pair<int, double> result = verifySplit(nums1, nums2, mid, leftOver - 1);
            if (result.first == -1) {
                // i1 needs to be greater, so we update lo
                lo = mid+1;
                loChanged = true;
            } else if (result.first == 1) {
                // i1 needs to be smaller, so we update hi
                hi = mid;
                loChanged = false;
            } else {
                // result.first == 0, we got the answer!
                return result.second;
            }
        }
        if (loChanged) {
            hi++;
        } else {
            lo--;
        }
        pair<int, double> result = verifySplit(nums1, nums2, lo, k - lo - 2);
        return result.second;
    }
};

int main(void) {
    Problem_4 solution;
    vector<int> nums1 = {2};
    vector<int> nums2 = {1};
    cout << "answer: " << solution.findMedianSortedArrays(nums1, nums2) << endl;
    return 0;
}