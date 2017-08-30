
def startAt(x:Int): (Int) => Int = {
   val incrementBy=(y:Int) => x+y
   return incrementBy
}

val closureFun1=startAt(10)  // ( y:Int )=> 10 + y

val closureFun2=startAt(100)  // (y:Int )=  100 + y

closureFun1(1000)

closureFun2(500)

val list1=List(1,2,3,4,5)

val list2 = list1 map ( e => closureFun1(e))





