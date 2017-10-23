import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/*
处理方法：
1）从数量上进行治理，减少发送进水缸里的事件；
2）从速度上进行治理。缓存事件发送进水缸的速度。
 */
fun main(args: Array<String>) {
//    rxjava6_1()
//    rxjava6_2()
//    rxjava6_3()
}

/*
控制上游速度，减慢上游发送事件速度。
 */
private fun rxjava6_3() {
    println("---------start 6_3-----------")
    Observable.create<Int> {
        var i = 0
        while (true) {
            it.onNext(i++)
            Thread.sleep(2000)
        }
    }.subscribe {
        println(it)
    }
    println("---------end 6_3-----------")
}

/*
sample 取样 隔两秒从上游取出一个事件发送给下游，
两秒间的事件上游尽管发，下游不会收到。
缺点：丢失了大量事件。
 */
private fun rxjava6_2() {
    println("---------start 6_2-----------")
    Observable.create<Int> {
        var i = 0
        while (true) {
            it.onNext(i++)
        }
    }.sample(
            2,
            TimeUnit.SECONDS
    ).subscribe {
        println(it)
    }
    println("---------end 6_2-----------")
}

private fun rxjava6_1() {
    println("---------start 6_1-----------")
    Observable.create<Int> {
        var i = 0
        while (true) {
            it.onNext(i++)
        }
    }.filter {
        it % 10 == 0
    }.subscribe {
        println(it)
    }
    println("---------end 6_1-----------")
}