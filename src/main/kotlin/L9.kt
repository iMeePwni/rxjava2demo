import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.io.FileReader

fun main(args: Array<String>) {
//    rxjava9_1()
//    errorMethod()
    Flowable.create(FlowableOnSubscribe<String> {
        FileReader("E:\\rxjava2demo\\src\\main\\resources\\test.txt")
                .readLines()
                .forEach(::println)
    },
            BackpressureStrategy.ERROR
    ).subscribe(object : Subscriber<String> {
        override fun onComplete() {
            println("onComplete")
        }

        override fun onError(t: Throwable?) {
            println("onError $t")
        }

        override fun onNext(t: String?) {
            println(t)
        }

        override fun onSubscribe(s: Subscription) {
            println("onSubscribe")
            s.request(Long.MAX_VALUE)
        }

    })
}

// 当文件很大时，全部读入内存，在进行操作是不可取的
private fun errorMethod() {
    FileReader("E:\\rxjava2demo\\src\\main\\resources\\test.txt")
            .readLines()
            .forEach(::println)
}

/*
当下游调用Subscription.request(long)时，上游就能够知道
下游能处理处理多少个事件。
 */
private fun rxjava9_1() {
    Flowable.create(FlowableOnSubscribe<Int> {
        (1..120).forEach { i ->
            println("current requested: ${it.requested()}")
            // 只有onNext会消耗requested,onError和onComplete不会。
            it.onNext(i)
        }
    },
            BackpressureStrategy.ERROR
    ).subscribe(object : Subscriber<Int> {
        override fun onComplete() {
        }

        override fun onNext(t: Int?) {
        }

        override fun onSubscribe(s: Subscription) {
            s.request(10) // 我要打十个
            s.request(100) // 我要打一百个！
        }

        override fun onError(t: Throwable?) {
        }

    })
}