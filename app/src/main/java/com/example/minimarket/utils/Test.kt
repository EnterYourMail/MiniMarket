package com.example.minimarket.utils

class Test {

    fun main1() {
//        val s = null.toString().orEmpty()
//        val nums = arrayOf(1, 2, 3, 1)
//        val two = nums.sliceArray(1..2)
//        val hash = HashSet<Int>()
//
//        val linkedNums = LinkedList(nums.asList())
//        linkedNums.isNotEmpty()
//        val ss = nums.mapIndexed { index, i ->  i to index}
//        val s2 = nums.withIndex(). { index, i -> i to index }.toMap()
//        var h: MutableMap<Int, Int> = HashMap()
//
//        val numi = arrayOf(1,2,3,0,0,0)
//        val numj = arrayOf(2,5,6)
//        var i: Int = 3
//        var j: Int = 3
//        var c = i + j - 1
//        i--
//        j--
//        while ( c > -1 ) {
//            if ( j < 0 || numi[i] > numj[j]) {
//                numi[c] = numi[i]
//                i--
//            } else {
//                numi[c] = numj[j]
//                j--
//            }
//            c--
//        }
//        val s = "dfdf"
//        s.replaceFirstChar { it.titlecase() }

//        val hs = "abcd".toHashSet()
//        for ( char in "abcde") {
//            if ( !hs.contains(char) ) {
//                print("true")
//            }
//        }
//
//        hs.removeAll("df".toSet())
//        val ch = hs.toCharArray()[0]
//        "dfdf".indices
//        val l = ArrayList<Int>()
//        l.add(1)
//        val ar = ArrayList<Int>()
//        h.getOrElse()
//        val arr = intArrayOf(1,2,3)
//        val asd = arr.toSet()
//        asd.maxOrNull()
//        max(1,2)
//        val i = Array<IntArray>(5) { IntArray(4) }
//        i[0] = IntArray(4)
//        val io = 1 % 3
//        val u = ArrayList<Int>(3)
//        val b = List<Int>(1) { init -> 1}
//        val listList = List(1) {
//                index -> ArrayList<Int>(index+1)
//        }
//        val nut = MutableList(1) {}
//        val er = mutableSetOf<>(1,2)
//        b.hashCode()
//        val s = "dfdf"
//        val hash = HashMap<Char, Int>()
//        s.forEachIndexed { index, char ->
//            hash[char]?.let {
//                if (it != -1 ) {
//                    hash[char] = -1
//                } else {
//                    hash[char] = index
//                }
//            }
//        }
//        val charHash = IntArray('z'-'a')
//        charHash.all { it > 0 }
//        val b = 'z' - 'a'
//        val s1= "dfdf"
//        val s2 = "fgf"
//
//        val res = s1.zip(s2).fold(0) { value, (ch1, ch2) -> value + (ch1 - ch2) }

//        val ar = arrayOf(charArrayOf('1', '0'), charArrayOf('0', '1'))
//        val lastRow = ar.lastIndex
//        val lastColumn = ar[0].lastIndex
//        val visited = HashSet<Int>()
//        val qq = ArrayDeque<Int>() //x + y * 1000
//
//        var fullIndex = 0
//        var curX = 0
//        var curY = 0
//        var island = 0
//        for ( (rowIndex, row) in ar.withIndex() ) {
//            for ( (columnIndex, cell) in row.withIndex() ) {
//                fullIndex = columnIndex + rowIndex * 1000
//                if ( cell == '1' && fullIndex !in visited ) {
//                    visited.add(fullIndex)
//                    qq.addLast(fullIndex)
//                    while ( qq.isNotEmpty() ) {
//                        fullIndex = qq.first()
//                        curX = fullIndex % 1000
//                        curY = fullIndex / 1000
//                        if (curX > 0 && ar[curY][curX - 1] == '1' && (fullIndex - 1) !in visited) {
//                            visited.add(fullIndex - 1)
//                            qq.addLast(fullIndex - 1)
//                        }
//                        if (curX < lastColumn && ar[curY][curX + 1] == '1' && (fullIndex + 1) !in visited) {
//                            visited.add(fullIndex + 1)
//                            qq.addLast(fullIndex + 1)
//                        }
//                        if (curY > 0 && ar[curY - 1][curX] == '1' && (fullIndex - 1000) !in visited) {
//                            visited.add(fullIndex - 1000)
//                            qq.addLast(fullIndex - 1000)
//                        }
//                        if (curY < lastRow && ar[curY + 1][curX] == '1' && (fullIndex + 1000) !in visited) {
//                            visited.add(fullIndex + 1000)
//                            qq.addLast(fullIndex + 1000)
//                        }
//                        qq.removeFirst()
//                    }
//                    island++
//                }
//            }
//        }
//        println(island)
//        val s = "(){}"
//        val char = s[0]
//        val ar = ArrayList<Int>()
//        val st = ArrayDeque<Char>()
//
//
//
//        var i = 0
//        i = minOf(1,2)
//        ar.last
//        Int.MAX_VALUE
//        data class Athlete(val index: Int, val score: Int)
//        class AthleteComparator: Comparator<Athlete> {
//            override fun compare(o1: Athlete, o2: Athlete): Int {
//                return o1.score - o2.score
//            }
//        }
//
//        val ar = intArrayOf(3,8,1)
//        val output = Array<String>(ar.size) { "" }
//        val heap = PriorityQueue<Athlete>(ar.size)  { o1, o2 -> o2.score - o1.score }
//        heap.addAll(ar.withIndex().map { (index, score) -> Athlete(index, score) } )
//
//        var cur: Athlete
//        var place = 0
//        while ( heap.isNotEmpty() && place < 3) {
//            place++
//            cur = heap.remove()
//            output[cur.index] = when(place) {
//                1 -> "Gold Medal"
//                2 -> "Silver Medal"
//                3 -> "Bronze Medal"
//                else -> "Error"
//            }
//        }
//
//        while ( heap.isNotEmpty() ) {
//            place++
//            cur = heap.remove()
//            output[cur.index] = place.toString()
//        }

    }
}