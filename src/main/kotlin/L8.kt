import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates
/*
正确理解上游事件发射太快，下游处理太慢这一矛盾。
Flowable替我们封装好了背压策略，但我们要知道问题的根源。
 */
fun main(args: Array<String>) {
//    rxjava8_1()
//    rxjava8_2()
//    rxjava8_3()
//    rxjava8_4()
}

/*
不是我们创建的Flowable,可以使用RxJava给我们提供的这些方法：
onBackpressureBuffer()
onBackpressureDrop()
onBackpressureLatest()
 */
private fun rxjava8_4() {
    Flowable.interval(1, TimeUnit.MILLISECONDS)
            .onBackpressureDrop { "you drop $it" }
            .subscribe(object : Subscriber<Long> {
                override fun onComplete() {
                    println("onComplete")
                }

                override fun onSubscribe(s: Subscription) {
                    println("onSubscribe")
                    s.request(1000)
                }

                override fun onNext(t: Long?) {
                    println(t)
                }

                override fun onError(t: Throwable?) {
                    println("onError $t")
                }

            })
}

private fun rxjava8_3() {
    println("-----------start 8_3-----------")
    var subscription: Subscription by Delegates.notNull()
    Flowable.create(FlowableOnSubscribe<Int> {
        (0..1000).forEach { i ->
            println("emit $i")
            it.onNext(i)
        }
        // LATEST就是只保留最新的事件
    }, BackpressureStrategy.LATEST
    ).observeOn(
            Schedulers.newThread()
    ).subscribe(object : Subscriber<Int> {
        override fun onNext(t: Int?) {
            println(t)
        }

        override fun onComplete() {
            println("onComplete")
        }

        override fun onError(t: Throwable?) {
            println("onError $t")
        }

        override fun onSubscribe(s: Subscription) {
            println("onSubscribe")
            subscription = s
            s.request(10)
        }
    })
    println("-----------end 8_3-----------")
}

private fun rxjava8_2() {
    println("-----------start 8_2-----------")
    Flowable.create(FlowableOnSubscribe<Int> {
        (0..1000).forEach { i ->
            println("emit $i")
            it.onNext(i)
        }
        // Drop就是直接把存不下去的时间丢弃
    }, BackpressureStrategy.DROP
    ).subscribe(object : Subscriber<Int> {
        override fun onNext(t: Int?) {
            println(t)
        }

        override fun onComplete() {
            println("onComplete")
        }

        override fun onError(t: Throwable?) {
            println("onError $t")
        }

        override fun onSubscribe(s: Subscription) {
            println("onSubscribe")
            s.request(10)
        }
    })
    println("-----------end 8_2-----------")
}

private fun rxjava8_1() {
    println("-----------start 8_1-----------")
    Flowable.create(FlowableOnSubscribe<Int> {
        (0..1000).forEach { i ->
            println("emit $i")
            it.onNext(i)
        }
        // 新水缸
    }, BackpressureStrategy.BUFFER
    ).subscribe(object : Subscriber<Int> {
        override fun onNext(t: Int?) {
            println(t)
        }

        override fun onComplete() {
            println("onComplete")
        }

        override fun onError(t: Throwable?) {
            println("onError $t")
        }

        override fun onSubscribe(s: Subscription?) {
            println("onSubscribe")
        }
    })
    println("-----------end 8_1-----------")
}