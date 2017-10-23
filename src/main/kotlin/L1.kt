import io.reactivex.Observable

fun main(args: Array<String>) {
    rxjava1_1()
    rxjava1_2()
}

private fun rxjava1_2() {
    println("------------start rxjava1_2------------")
    Observable.create<String> {
        it.onNext("H")
        it.onNext("E")
        it.onNext("L")
        it.onNext("L")
        it.onComplete()
        it.onNext("O")
    }.subscribe {
        println(it)
    }
    println("------------end rxjava1_2------------")
}

private fun rxjava1_1() {
    println("------------start rxjava1_1------------")
    Observable.create<String> {
        it.onNext("Hello")
        it.onNext("World")
        it.onComplete()
    }.subscribe({
        println(it)
    }, {
        println("onError")
    }, {
        println("onComplete")
    })
    println("------------end rxjava1_1------------")
}