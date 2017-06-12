package produce;

import java.math.BigDecimal;
import java.util.Arrays;

/*
 * 归一化，标准化可以 把数变为（0，1）之间的小数
 * 主要是为了数据处理方便提出来的，把数据映射到0～1范围之内处理
 * http://blog.csdn.net/u011278704/article/details/51549384
其中μ为所有样本数据的均值，σ为所有样本数据的标准差。
如果利用3σ进行规一化，落在 [-1 , 1] 之间的概率是99.7%，在实际应用中我们将落在[-1 , 1] 区间之外的值均设成-1和1，以保证所有的数值均落在[-1 , 1] 范围之内。
 * 
 */

public class Normalization {
	
	
	//获得0到1之间的小数
	public static String getbetween01 (String str,double average,double standardDevition) {
		String rawnumstr = str.substring(str.indexOf("="),str.length());
		String numstr = rawnumstr.substring(1,rawnumstr.length());
//		System.out.println(numstr);
		int num = Integer.parseInt(numstr); 
		double normal = (double)(num-average)/standardDevition;	
		return (str + "概率"+normal);
	}
	
	
	public static  String[] Nu2Probability (String[] arr) {
 		int [] noarr = new int[arr.length];
		//只有一个数据
		if (arr.length ==1){
			return arr;
		//只有两个数据
		}else if(arr.length == 2){
			String sa = arr[0];
			String sb = arr[1];
//			System.out.println(sa.substring(sa.lastIndexOf("="),sa.length()).substring(1));
//			System.out.println(sb.substring(sb.lastIndexOf("="),sb.length()).substring(1));
			int a = Integer.parseInt(sa.substring(sa.lastIndexOf("="),sa.length()).substring(1));	
			int b = Integer.parseInt(sb.substring(sb.lastIndexOf("="),sb.length()).substring(1));
			if(a - b > 3){
				arr[0] = arr[0]+"2值差距>3";
				arr[1] = arr[1]+"2值差距>-3";
				return arr;
			}else{
				arr[0] = arr[0]+"2值差距<3";
				arr[1] = arr[1]+"2值差距<3";
				return arr;
			}

		//三个以上数据
		}else{
			
			for(int i=0;i<arr.length;i++){
//				System.out.println(arr[i]);
				noarr[i] = Integer.parseInt(arr[i].substring(arr[i].lastIndexOf("="),arr[i].length()).substring(1));
//				System.out.println("noarr"+noarr[i]);
			}	
			double average = getAverage(noarr).doubleValue();
//			System.out.println("平均值为"+getAverage(noarr).doubleValue());
			double standardDevition = getStandardDevition(noarr).doubleValue();
			for(int i=0;i<arr.length;i++){
//				System.out.println(arr[i]);
				//通过函数获得每个数字在1和0间的映射
				arr[i] = getbetween01(arr[i],average,standardDevition);
//				System.out.println(noarr[i]);
			}	
			return  arr;
		}

	}

	
    //獲取平均值
    public static BigDecimal getAverage (int[] arr) throws ArithmeticException{
        int sum = 0;
		for(int i = 0;i < arr.length;i++){
		    sum += arr[i];
		}
//		System.out.println("数值的和为"+sum);
		BigDecimal bigDecimal;
		bigDecimal = new BigDecimal((double) sum / (arr.length));
//		System.out.println("数值的平均值为"+bigDecimal.doubleValue());
		return (bigDecimal);
		}

    
    
    
    
    //獲取標準差,利用BigDecimal防止出现除不尽的情况,原方法地址为http://blog.sina.com.cn/s/blog_6c851f01010160g2.html
    public static BigDecimal getStandardDevition(int[] arr) throws ArithmeticException{
    	double sum = 0;
        for(int i = 0;i < arr.length;i++){
        	// .doubleValue()将BigDecimal类型的数据转为double
            sum += ((double)arr[i] -getAverage(arr).doubleValue()) * (arr[i] -getAverage(arr).doubleValue());
           
        }
        
        BigDecimal bigDecimal = new BigDecimal(Math.sqrt(sum / (arr.length-1)));
		System.out.println("标准差为"+bigDecimal.doubleValue());
        return (bigDecimal);
    }
    

    
	public static void main(String[] args) {
		String[] arr = {"哥斯达黎加=1300","哥斯达黎加=1200","巴西=1100"};
		arr = (Nu2Probability(arr));
		System.out.println(Arrays.toString(arr));
		//取得数组里面的最大值和最小值

	}
	

}