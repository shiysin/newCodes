public class SortPrimer {

    static int count = 0;

    // 冒泡排序
    public static int[] sort(int[] array){
        //这里for循环表示总共需要比较多少轮
        for(int i = 1 ; i < array.length; i++){
            //设定一个标记，若为true，则表示此次循环没有进行交换，也就是待排序列已经有序，排序已经完成。
            boolean flag = true;
            //这里for循环表示每轮比较参与的元素下标
            //对当前无序区间array[0......length-i]进行排序
            //j的范围很关键，这个范围是在逐步缩小的,因为每轮比较都会将最大的放在右边
            for(int j = 0 ; j < array.length-i ; j++){
                if(array[j]>array[j+1]){
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                    flag = false;
                }
                count++;
            }
            if(flag){
                break;
            }
            //第 i轮排序的结果为
//            System.out.print("第"+i+"轮排序后的结果为:");
//            display(array);

        }
        return array;
    }

    // 选择排序
    public static int[] selectSort(int[] array){
        //总共要经过N-1轮比较
        for(int i = 0 ; i < array.length-1 ; i++){
            int min = i;
            //每轮需要比较的次数
            for(int j = i+1 ; j < array.length ; j++){
                if(array[j]<array[min]){
                    min = j;//记录目前能找到的最小值元素的下标
                }
                count++;
            }
            //将找到的最小值和i位置所在的值进行交换
            if(i != min){
                int temp = array[i];
                array[i] = array[min];
                array[min] = temp;
            }
            //第 i轮排序的结果为
//            System.out.print("第"+(i+1)+"轮排序后的结果为:");
//            display(array);
        }
        return array;
    }

    // 插入排序
    public static int[] insertSort(int[] array){
        int j;
        //从下标为1的元素开始选择合适的位置插入，因为下标为0的只有一个元素，默认是有序的
        for(int i = 1 ; i < array.length ; i++){
            int tmp = array[i];//记录要插入的数据
            j = i;
            while(j > 0 && tmp < array[j-1]){//从已经排序的序列最右边的开始比较，找到比其小的数
                array[j] = array[j-1];//向后挪动
                j--;
                count++;
            }
            array[j] = tmp;//存在比其小的数，插入
        }
        return array;
    }

    //遍历显示数组
    public static void display(int[] array){
        for(int i = 0 ; i < array.length ; i++){
            System.out.print(array[i]+" ");
        }
        System.out.println();
    }

    public static void main(String[] args){
        System.out.println("-----------------------");

        int[] sort = {4,2,8,9,5,7,6,1,3};
        sort(sort);
        System.out.println("冒泡：" + count);

        count = 0;
        int[] selectSort = {4,2,8,9,5,7,6,1,3};
        selectSort(selectSort);
        System.out.println("选择：" + count);

        count = 0;
        int[] insertSort = {4,2,8,9,5,7,6,1,3};
        insertSort(insertSort);
        System.out.println("插入：" + count);

        System.out.println("-----------------------");
    }

}

