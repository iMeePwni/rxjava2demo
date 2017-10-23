import io.reactivex.Observable
import io.reactivex.functions.BiFunction

fun main(args: Array<String>) {
    rxjava4_1()
}

private fun rxjava4_1() {
    val intObservable = Observable.create<Int> {
        println(" emit 1")
        it.onNext(1)
        println(" emit 2")
        it.onNext(2)
        println(" emit 3")
        it.onNext(3)
        println(" emit 4")
        it.onNext(4)
        println(" emit 5")
        it.onNext(5)
    }
    val stringObservable = Observable.create<String> {
        println("emit A")
        it.onNext("A")
        println("emit B")
        it.onNext("B")
        println("emit C")
        it.onNext("C")
    }
    Observable.zip<Int, String, String>(
            intObservable,
            stringObservable,
            BiFunction { i, s -> "$i : $s" }
    ).subscribe {
        println(it)
    }
}