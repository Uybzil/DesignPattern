package org.uncle.lee.singleton.lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.uncle.lee.utils.LogUtils;

public class Client {
	private static final String TAG = "LazySingletonClient";
	
	private static int TOTAL_RUNING_TIME = 20;
	private static int CHECK_CODE = LazySingleton.getInstance().hashCode();
	
	public static void main(String[] args){
		Callable<Integer> callable = getCallable();
		List<Callable<Integer>> callableList = getCallableList(callable);
		
		testInstanceCatchException(callableList);
	}

	private static Callable<Integer> getCallable() {
		return new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				LazySingleton instance = LazySingleton.getInstance();
				instance.showInstanceAddress();
				return instance.hashCode();
			}
		};
	}
	
	private static List<Callable<Integer>> getCallableList(Callable<Integer> callable) {
		List<Callable<Integer>> callableList = new ArrayList<Callable<Integer>>();
		for(int i = 0; i < TOTAL_RUNING_TIME; i++){
			callableList.add(callable);
		}
		return callableList;
	}
	
	private static void testInstanceCatchException(List<Callable<Integer>> callableList) {
		try {
			testInstance(callableList);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e){
			e.printStackTrace();
		}
	}

	private static void testInstance(List<Callable<Integer>> callableList) throws InterruptedException, ExecutionException {
		ExecutorService poll = Executors.newFixedThreadPool(TOTAL_RUNING_TIME);
		
		List<Future<Integer>> responseCodes = poll.invokeAll(callableList);
		testresponseCode(responseCodes);
	}

	private static void testresponseCode(List<Future<Integer>> responseCodes) throws InterruptedException, ExecutionException {
		for(Future<Integer> future : responseCodes){
			if(future.get() != CHECK_CODE){
				showcheckFailureMessage(future.get());
				return;
			}
		}
		showCheckSuccessfulMessage();
	}

	private static void showcheckFailureMessage(Integer failureCode) {
		LogUtils.e(TAG, "check error, thread unsafe. Failure code : " + failureCode);
	}
	
	private static void showCheckSuccessfulMessage() {
		LogUtils.d(TAG, "check success, thread safe");
	}
}