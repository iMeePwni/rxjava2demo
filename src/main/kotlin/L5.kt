import io.reactivex.Observable

fun main(args: Array<String>) {
    /*
    当上下游在同一个线程中，订阅关系为同步，上游发送一个事件后必须等待下游接受处理完事件才能发送下一个事件；
    当上下游在不同线程时，订阅关系为异步，上游发送下一次事件与下游是否处理完事件无关。
    同步和异步的区别仅仅在于是否上下游之间是否有“水缸”。
    当我们以后遇到“BackPressure”时，仔细思考一下“水缸”在哪里，找到水缸，你就能找到解决问题的方法。
     */
    Observable.create<Int> {
        while (true) {
            it.onNext(1)
        }
    }.subscribe {
        Thread.sleep(2000)
        println(it)
    }
}