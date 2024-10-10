package com.car.hailing.function;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: fanbiao.fu
 * @Description:
 * @Date: 2024/8/27
 */
public class ParamUtil {



//  public static ParamExceptionFunction isTrue(boolean check){
//    return (errorMsg)->{
//     if(check){
//       throw new ParamException(errorMsg);
//     }
//    };
//  }


  public static <T> void sync(List<T> list, int subIndex, Consumer<? super List<T>> fn) {
    ExecutorService executor = Executors.newFixedThreadPool(4);
    // 切割List
    List<List<T>> chunks = splitList(list, subIndex);
    // 使用CompletableFuture并行处理每个切割后的List
    chunks.stream()
        .map(CompletableFuture::completedFuture)
        .map(cf -> cf.thenAcceptAsync(fn,executor))
        .forEach(CompletableFuture::join); // 等待所有CompletableFuture完成
  }

  public static <T> List<Object>  sync(List<T> list, int subIndex, Function<? super List<T>,Object> fn)
      throws ExecutionException, InterruptedException {
    ExecutorService executor = Executors.newFixedThreadPool(4);
    // 切割List
    List<List<T>> chunks = splitList(list, subIndex);
    // 使用CompletableFuture并行处理每个切割后的List
    List<CompletableFuture<Object>> results = chunks.stream()
        .map(CompletableFuture::completedFuture)
        .map(cf -> cf.thenApplyAsync(fn, executor)).collect(Collectors.toList());
    // 等待所有CompletableFuture完成
    results
        .forEach(CompletableFuture::join);
    //结果返回
    List<Object> collect = new ArrayList<>();
    for (CompletableFuture<Object> result : results) {
      Object object = result.get();
      collect.add(object);
    }
    return collect;
  }


  public static <T> List<List<T>> splitList(List<T> values, int subIndex) {
    int splitcount = (values.size() / subIndex) + 1;
    List<List<T>> collect = Stream.iterate(0, n -> n + subIndex)
        .limit(splitcount)
        .map(item -> {
          return values.parallelStream().skip(item).limit(subIndex).collect(Collectors.toList());
        })
        .collect(Collectors.toList());
    return collect;

  }

}
