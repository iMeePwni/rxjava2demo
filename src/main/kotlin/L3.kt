import io.reactivex.Observable

fun main(args: Array<String>) {
    rxjava3_1()
    rxjava3_2()
    rxjava3_3()
    rxjava3_4()
}

private fun rxjava3_4() {
    println("----------start 3_4--------------")
    Observable.create<String> {
        it.onNext("注册成功")
    }.doOnNext {
        println("先根据注册的响应结果去做一些操作")
    }.flatMap {
        // flatMap 的作用就是把一个Observable转换为另一个Observable
        println(it)
        Observable.create<Int> {
            // 登陆成功
            it.onNext(200)
        }
    }.subscribe {
        println(it)
    }
    println("----------end 3_4--------------")
}

private fun rxjava3_3() {
    println("----------start 3_3--------------")
    Observable.create<Int> {
        it.onNext(1)
        it.onNext(2)
        it.onNext(3)
    }.concatMap {
        val list = List(3) { index ->
            "$index : $it"
        }
        Observable.fromIterable(list)
    }.subscribe {
        println(it)
    }
    println("----------end 3_3--------------")
}

private fun rxjava3_2() {
    println("----------start 3_2--------------")
    Observable.create<Int> {
        it.onNext(1)
        it.onNext(2)
        it.onNext(3)
    }.flatMap {
        val list = List(3) { index ->
            "$index : $it"
        }
        Observable.fromIterable(list)
    }.subscribe {
        println(it)
    }
    println("----------end 3_2--------------")
}

private fun rxjava3_1() {
    println("----------start 3_1--------------")
    Observable.create<Int> {
        it.onNext(1)
        it.onNext(2)
        it.onNext(3)
        it.onNext(4)
    }.map {
        // 事件转换
        "This is result $it"
    }.subscribe {
        println(it)
    }
    println("----------end 3_1--------------")
}
