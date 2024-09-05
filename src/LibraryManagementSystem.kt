
import kotlin.system.exitProcess






class LibraryManagementSystem {
    private val books = mutableListOf<Book>()
    private val members = mutableListOf<Member>()
    private val borrowedBooks = mutableMapOf<Int, Int>()

    init {
        books.add(Book(1, "1984", "George Orwell", true))
        books.add(Book(2, "To Kill a Mockingbird", "Harper", true))
        books.add(Book(3, "The Great Gatsby", "F. Scott Fitzgerald", true))
        books.add(Book(4, "Moby Dick", "Herman Melville", true))
        books.add(Book(5, "Pride and Prejudice", "Jane Austen", true))
        books.add(Book(6, "War and Peace", "Leo Tolstoy", true))
        books.add(Book(7, "The Catcher in the Rye", "J.D. Salinger", true))
        books.add(Book(8, "Brave New World", "Aldous Huxley", true))
        books.add(Book(9, "The Hobbit", "J.R.R. Tolkien", true))
        books.add(Book(10, "Fahrenheit 451", "Ray Bradbury", true))


        members.add(Member(1, "Alice Smith", "555-1234"))
        members.add(Member(2, "Bob Johnson", "555-5678"))
        members.add(Member(3, "Charlie Brown", "555-8765"))
        members.add(Member(4, "Diana Prince", "555-4321"))
        members.add(Member(5, "Eve Adams", "555-6789"))
        members.add(Member(6, "Frank Castle", "555-3456"))
        members.add(Member(7, "Grace Lee", "555-2345"))
        members.add(Member(8, "Hank Hill", "555-7890"))
        members.add(Member(9, "Ivy Clark", "555-6780"))
        members.add(Member(10, "Jack Daniels", "0175558901"))

    }

    private fun userLogin(): Boolean {
        println("Enter username:")
        val username = readLine()!!
        println("Enter password:")
        val password = readLine()!!

        return username == "librarian" && password == "lib123"
    }

    private fun searchBooks(query: String) {
        val results = books.filter {
            it.author.contains(query, ignoreCase = true) ||
                    it.genre.contains(query, ignoreCase = true)
        }
        if (results.isEmpty()) {
            println("No books found for query: $query")
        } else {
            results.forEach() {
                println("ID: ${it.id}, Author: ${it.author}, Genre: ${it.genre}")
            }
        }
    }

    fun markBook(status: String) {
        println("Enter Book ID to $status:")
        val bookId = readLine()?.toIntOrNull() ?: return println("Invalid Book ID")

        val book = books.find { it.id == bookId } ?: return println("Book not found")

        if (status == "borrow" && book.isAvailable) {
            book.isAvailable = false
            println("Book ID $bookId marked as borrowed.")
            println("Enter Member ID who borrowed the book:")
            val memberId = readLine()?.toIntOrNull() ?: return println("Invalid Member ID")
            borrowedBooks[bookId] = memberId
        } else if (status == "return" && !book.isAvailable) {
            book.isAvailable = true
            println("Book ID $bookId marked as returned.")
            borrowedBooks.remove(bookId)
        } else {
            println("Book is already ${if (book.isAvailable) "available" else "borrowed"}.")
        }
    }

    private fun viewBooks(availableOnly: Boolean) {
        val filteredBooks = if (availableOnly) {
            books.filter { it.isAvailable }
        } else {
            books
        }
        filteredBooks.forEach {
            println("ID: ${it.id}, Author: ${it.author}, Genre: ${it.genre}, Available: ${it.isAvailable}")
        }
    }

    private fun viewMembers() {
        members.forEach {
            println("ID: ${it.id}, Name: ${it.name}, Contact Info: ${it.contactInfo}")
        }
    }

    private fun borrowedBooksStatus() {
        if (borrowedBooks.isEmpty()) {
            println("No books are currently borrowed.")
        } else {
            borrowedBooks.forEach { (bookId, memberId) ->
                val book = books.find { it.id == bookId } ?: return
                val member = members.find { it.id == memberId } ?: return
                println("Book '${book.id}' (ID: $bookId) is borrowed by ${member.name} (ID: $memberId)")
            }
        }
    }

    fun start() {
        if (userLogin()) {
            println("Login success...")


            while (true) {
                println("\nLibrary Management System")
                println("1. Search books")
                println("2. Mark book as borrowed")
                println("3. Mark book as returned")
                println("4. View list of books")
                println("5. View available books")
                println("6. View list of members")
                println("7. View borrowed books status")
                println("0. Exit")
                print("Enter your choice: ")
                when (readLine()?.toIntOrNull()) {
                    1 -> {
                        print("Enter search query (title/author/genre): ")
                        val query = readLine().orEmpty()
                        searchBooks(query)
                    }

                    2 -> markBook("borrow")
                    3 -> markBook("return")
                    4 -> viewBooks(availableOnly = false)
                    5 -> viewBooks(availableOnly = true)
                    6 -> viewMembers()
                    7 -> borrowedBooksStatus()
                    0 -> {
                        println("Exiting...")
                        exitProcess(0)
                    }

                    else -> println("Invalid choice. Please try again.")
                }
            }
        }
    }
}