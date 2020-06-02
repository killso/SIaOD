public class Interpolation {
    public static int InterpolationSearch(int[] massiv, int a) {
        int mid = 0;
        int small = 0;
        int big = massiv.length-1;
        while (massiv[small]<a&&massiv[big]>a) {
            mid=small+((a-massiv[small])*(big - small))/(massiv[big]-massiv[small]);
            if (massiv[mid]<a)
                small=mid+1;
            else if (massiv[mid]>a)
               big = mid-1;
            else
                return mid;
        }
        if (massiv[small]==a)
            return small;
        if (massiv[big]==a)
            return big;
        return -1; // Не найдено
    }
}
