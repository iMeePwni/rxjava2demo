import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

fun main(args: Array<String>) {
    rxjava7_1()
}

private fun rxjava7_1() {
    // 水缸BUFFER_SIZE 128
    Flowable.create(FlowableOnSubscribe<Int> {
        println("emit 1")
        it.onNext(1)
        println("emit 2")
        it.onNext(2)
        println("emit 3")
        it.onNext(3)
        println("emit 4")
        it.onNext(4)
    }, // 和Observable不同，多出来的参数是用来选择被压的
            BackpressureStrategy.ERROR
    ).subscribe(object : Subscriber<Int> {
        override fun onComplete() {
            println("onComplete")
        }

        /*
        subscribe中传入的参数为Subscribe而不是Disposable
        可以利用s.cancel()切断水管
         */
        override fun onSubscribe(s: Subscription) {
            println("onSubscribe")
            // 响应式拉取
            // 可以将s提取出来调用request依次拉取水缸中上游内容
            s.request(2)
        }

        override fun onError(t: Throwable?) {
            println("onError $t")
        }

        override fun onNext(t: Int?) {
            println("onNext $t")
        }

    })
}