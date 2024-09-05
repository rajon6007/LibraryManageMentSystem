package Library

import kotlin.system.exitProcess

data class book (val id:Int, val title:String, val author:String, val genre:String, var isAvailable:Boolean)
data class member (val id:Int,val name:String,val cInfo:String)

private fun userlogin():Boolean {
    println("Enter username")
    val userName= readLine()!!
    println("Enter password")
    val password = readLine()!!

    return userName == "librarian" && password == "lib123"
}

class LMsystem{
    private val books = mutableListOf<book>()
    private val members = mutableListOf<member>()
    private val borrowedBook = mutableMapOf<Int,Int>()

    init {
        books.add(book(1,"vasha o sahityo","Soumitra sekhar","sahityo",true))
        books.add(book(1,"vasha o sahityo","Soumitra sekhar","sahityo",true))
        books.add(book(1,"vasha o sahityo","Soumitra sekhar","sahityo",true))
        books.add(book(1,"vasha o sahityo","Soumitra sekhar","sahityo",true))
        books.add(book(1,"vasha o sahityo","Soumitra sekhar","sahityo",true))

        members.add(member(1,"rajon","0451154"))
        members.add(member(1,"rajon","0451154"))


    }
    private fun searchBooks(query:String){
        val results = books.filter {
            it.author.contains(query,ignoreCase = true) || it.genre.contains(query,ignoreCase = true)
        }
        if(results.isEmpty()){
            println("No Book for query : $query")
        } else{
            results.forEach(){
                println("ID: ${it.title}, Author: ${it.author}, Genre: ${it.genre}")
            }
        }
    }
    fun markBooks(status:String){
        println("Enter Bookd Id to $status")
        val bookId = readLine()?.toIntOrNull() ?: return println("Invalid number Id")
        val book = books.find { it.id==bookId } ?: return println("Book not found")

        if (status=="borrow" && book.isAvailable) {
            book.isAvailable = false
            println("Book Id $bookId markeed as borrowed.")
            println("Enter menber id who borrowed the book: ")
            val memberId = readLine()?.toIntOrNull() ?: return println("Invalid member id")
            borrowedBook[bookId] = memberId
        } else if (status == "borrow" && !book.isAvailable){
            book.isAvailable=true
            println("Book Id $bookId marked as returned.")
            borrowedBook.remove(bookId)
        }else{
            println("Book is already ${if (book.isAvailable) "available" else "borrowed"}.")
        }
    }
    private fun viewBooks(availableOnly:Boolean){
        val filteredBook= if (availableOnly){
            books.filter { it.isAvailable }
        }else{
            books
        }
        filteredBook.forEach{
            println("ID: ${it.id}, Author: ${it.author}, Genre: ${it.genre}, Availability: ${it.isAvailable}")
        }
    }
    private fun viewMembers(){
        members.forEach {
            println("Id: ${it.id}, Name: ${it.name}, ContactInfo: ${it.cInfo}")
        }
    }
    private fun borrowedBook(){
        if (borrowedBook.isEmpty()){
            println("No books are currrently borrowed.")
        } else{
            borrowedBook.forEach { (bookId, memberId) ->
                val book = books.find { it.id==bookId }?: return
                val member = members.find { it.id==memberId }?: return
                println("Book '${book.id}' (Id: $bookId) is borrowed by ${member.name} (Id: $memberId)")
            }
        }
    }
    fun start(){
        if (userlogin()){
            println("Login success...")

            while (true){
                println("\nLibrary management system")
                println("1. search books")
                println("2. mark book as borrow ")
                println("3. mark book as return")
                println("4. view list of books")
                println("5. view available book")
                println("6. view list of member")
                println("7. view borrowed books status")
                println("0. Exit")
                print("Enter your choice : ")
                when(readLine()?.toIntOrNull()){
                    1 -> {
                        print("enter search query (title/author/genre): ")
                        val query = readLine().orEmpty()
                        searchBooks(query)
                    }
                    2 -> markBooks("borrow")
                    3 -> markBooks("return")
                    4 -> viewBooks(availableOnly = false)
                    5 -> viewBooks(availableOnly = true)
                    6 -> viewMembers()
                    7 -> borrowedBook()
                    0 -> {
                        println("Exiting")
                        exitProcess(0)
                    }
                    else -> println("Invalid choice... Please try again")
                }
            }
        }
    }


}

fun main(args: Array<String>) {
    val libraryManagementSystem = LMsystem()
    libraryManagementSystem.start()
    println()
}