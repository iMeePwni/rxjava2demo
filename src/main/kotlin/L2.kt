import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

fun main(args: Array<String>) {
    rxjava2_1()
    // http://www.jianshu.com/p/8818b98c44e2
    // 利用Retrofit进行网络操作；
    // 读写数据库操作。
}

private fun rxjava2_1() {
    // subscribeOn 指定上游线程，但是只有第一次指定有效。
    // observeOn 指定下游线程，每调用一次便会切换一次。
    // Schedulers.io()代表io操作的线程，通常用于网络，读写文件等io密集型的操作。
    // Schedulers.computation()代表CPU计算密集型的操作，例如需要大量计算的操作。
    // Schedulers.newThread()代表一个常规的线程。
    // AndroidSchedulers.mainThread()代表Android的主线程。
    Observable.create<String> {
        println("Emit thread ${Thread.currentThread().name}")
        it.onNext("Hello")
        it.onNext("World")
    }.observeOn( // 指定下游线程
            Schedulers.newThread()
    ).subscribe {
        println("onNext thread ${Thread.currentThread().name}")
        println(it)
    }
}