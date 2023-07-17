open class TransaksiEmas(val berat: Double, val npwp: Boolean) {
    /*Merupaka class yang merepresentasikan transaksi emas yang terdapat dua
     properti dan dua fungsi */

   internal object Batasan {  /* objek class yang digunakan untuk menetapkan batasan harga pada
         program sesuai dengan soal */
        internal val HARGA_BELI_PER_GRAM = 850000
        internal val HARGA_JUAL_PER_GRAM = 900000
        internal val PAJAKJUAL_DENGAN_NPWP = 1.5
        internal val PAJAKJUAL_TANPA_NPWP = 3.0
        internal val PAJAKBELI_DENGAN_NPWP = 0.45
        internal val PAJAKBELI_TANPA_NPWP = 0.9
        internal val MIN_BELI_GRAM = 0.05
        internal val MAX_BELI_GRAM = 80.0
        internal val MIN_JUAL_GRAM = 1.0
        internal val MAX_JUAL_GRAM = 100.0
    }

    open fun hitungHargaBeli(): Double {
        /* fungsi turunan dalam class TransaksiEmas untuk menghitung harga beli emas
        fungsi ini digunakan untuk menghitung harga beli emas berdasarkan berat emas yang
        diinputkan*/
        var hargaTotal = Batasan.HARGA_BELI_PER_GRAM * berat
        val pajak = if (npwp) Batasan.PAJAKBELI_DENGAN_NPWP else Batasan.PAJAKBELI_TANPA_NPWP
        val jumlahPajak = hargaTotal * pajak / 100
        hargaTotal += jumlahPajak
        return hargaTotal
    }

    open fun hitungHargaJual(): Double {
        var hargaTotal = Batasan.HARGA_JUAL_PER_GRAM * berat
        /* fungsi ini adalah sebuah open function turunan dalan class transaksi yang menghitung yang bertujuan untuk memeriksa object batasan berat penjualan dan
         menghitung harga jual emas. yang mengembalikan nilai berupa double.
         */
        val pajak = if (npwp) Batasan.PAJAKJUAL_DENGAN_NPWP else Batasan.PAJAKJUAL_TANPA_NPWP
        val jumlahPajak = hargaTotal * pajak / 100
        hargaTotal += jumlahPajak
        return hargaTotal
    }
}

class TransaksiBeliEmas(berat: Double, npwp: Boolean) : TransaksiEmas(berat, npwp) {
    // Class yang merupakan turunan dari class transaksi emas dan digunakan untuk
    // melakukan transaksi pembelian emas
    override fun hitungHargaBeli(): Double {
        /* overide diatas digunakan untuk mengimplementasikan
        metode hitungHargaBeli dari class parentnya yaitu transaksiEmas. Fungsi ini mengembalikan
        nilai Double yang merupakan harga beli emas.
         */
        val hargaTotal = super.hitungHargaBeli()
        if (berat < Batasan.MIN_BELI_GRAM || berat > Batasan.MAX_BELI_GRAM) {
            println("Berat emas untuk pembelian harus antara ${Batasan.MIN_BELI_GRAM} " +
                    "dan ${Batasan.MAX_BELI_GRAM} gram")
            return 0.0
        }
        if (hargaTotal < Batasan.MIN_BELI_GRAM * Batasan.HARGA_BELI_PER_GRAM) {
            return Batasan.MIN_BELI_GRAM * Batasan.HARGA_BELI_PER_GRAM
        }
        if (hargaTotal > Batasan.MAX_BELI_GRAM * Batasan.HARGA_BELI_PER_GRAM) {
            return Batasan.MAX_BELI_GRAM * Batasan.HARGA_BELI_PER_GRAM
        }
        return hargaTotal
    }
}

class TransaksiJualEmas(berat: Double, npwp: Boolean) : TransaksiEmas(berat, npwp) {
    // Class Ini merupakan turunan dari class transaksiEmas dan
    // digunakan untuk penjualan emas
    override fun hitungHargaJual(): Double {
        /* sama seperti fungsi sebelumny yaitu beli, overide fungsi hitungHargaJual diatas
        digunakan untuk mengimplementasikan metode hitungHargaBeli dari class parentnya yaitu ]
        transaksiEmas. Fungsi ini mengembalikan nilai Double yang merupakan harga jual barang.
        Perhitungan harga jual dengan polimorfisme */
        val hargaTotal = super.hitungHargaJual()
        if (hargaTotal < Batasan.MIN_JUAL_GRAM * Batasan.HARGA_JUAL_PER_GRAM) {
            return Batasan.MIN_JUAL_GRAM * Batasan.HARGA_JUAL_PER_GRAM /* control flow
            untuk memeriksa minimum penjualan emas*/
        }
        if (hargaTotal > Batasan.MAX_JUAL_GRAM * Batasan.HARGA_JUAL_PER_GRAM) {
            return Batasan.MAX_JUAL_GRAM * Batasan.HARGA_JUAL_PER_GRAM
            /* sebaliknya control flow
            untuk memeriksa maksimum penjualan emas */
        }
        return hargaTotal
    }
}
fun main() {
    try {
        // Meminta input pilihan dari user
        println("Selamat datang di program transaksi emas")
        println("1. Jual Emas")
        println("2. Beli Emas")
        print("Silakan pilih menu [1/2]: ")
        val pilihan = readLine()?.toInt()

        when (pilihan) { /* digunakan untuk memvalidasi input status npwp
        yang diinputkan oleh user.
        */
            1 -> {
                // Pilihan menu 1 yaitu jual emas
                println("\nAnda memilih menu Jual Emas")
                // Meminta input berat emas dari user
                print("Masukkan berat emas (gram): ")
                val beratEmas = readLine()?.toDouble()
                if (beratEmas == null || beratEmas <= 0) { /*
                jika input user tidak dapat konversi ke double, null
                atau input kurang dari atau sama dengan 0 maka kode
                tersebut akan menampilkan pesan berat emas tidak valid ke konsol
                */
                    println("Berat emas tidak valid")
                    return
                }

                // Memeriksa batasan minimal dan maksimal untuk transaksi jual
                if (beratEmas < TransaksiEmas.Batasan.MIN_JUAL_GRAM ||
                    beratEmas > TransaksiEmas.Batasan.MAX_JUAL_GRAM) {
                    println("Berat emas untuk penjualan harus antara " +
                            "${TransaksiEmas.Batasan.MIN_JUAL_GRAM} dan " +
                            "${TransaksiEmas.Batasan.MAX_JUAL_GRAM} gram")
                    return
                }

                // Meminta input status NPWP dari pengguna
                var npwp = false // mendeklarasikan npwp sebagai bollean (false)
                var validInput = false
                while (!validInput) { // Loop while akan terus berjalan selama variabel bernilai false
                    try { /* merupakan sebuah blok exception dgigunakan untuk menjalankan kode didalam blok kode
                    jika kode didalam berhasil dijalankan maka blok catch tidak akan dijalankan. jika kode dalam blok
                    gagal dirun maka blok catch akan dijalankan untuk menampilkan pesan eror*/
                        print("Apakah Anda memiliki NPWP? [y/t]: ")
                        val inputNpwp = readLine()
                        if (inputNpwp == "y" || inputNpwp == "t") {
                            npwp = inputNpwp == "y"
                            validInput = true
                        } else {
                            throw Exception("Input pilihan NPWP tidak valid, pilihan hanya Y dan T. Masukan Ulang")
                        }
                    } catch (e: Exception) {
                        println(e.message)
                    }
                }

                // Membuat objek transaksi jual emas
                val transaksi = TransaksiJualEmas(beratEmas, npwp)

                // Menghitung dan mencetak harga jual emas
                val hargaJual = transaksi.hitungHargaJual()
                val formatHargaJual = hargaJual.toInt()
                println("Harga jual emas: Rp $formatHargaJual")
            }
            2 -> {
                // Pilihan menu beli emas
                println("\nAnda memilih menu Beli Emas")
                // Meminta input berat emas dari pengguna
                print("Masukkan berat emas (gram): ")
                val beratEmas = readLine()?.toDouble()
                if (beratEmas == null || beratEmas <= 0) {
                    println("Berat emas tidak valid")
                    return
                }

                // Memeriksa batasan minimal dan maksimal untuk transaksi beli
                if (beratEmas < TransaksiEmas.Batasan.MIN_BELI_GRAM
                    || beratEmas > TransaksiEmas.Batasan.MAX_BELI_GRAM) {
                    println("Berat emas untuk pembelian harus antara " +
                            "${TransaksiEmas.Batasan.MIN_BELI_GRAM} dan " +
                            "${TransaksiEmas.Batasan.MAX_BELI_GRAM} gram")
                    return
                }

                // Meminta input status NPWP dari user
                var npwp = false
                var validInput = false
                while (!validInput) {
                    try {
                        print(" memiliki NPWP? [y/t]: ")
                        val inputNpwp = readLine()
                        if (inputNpwp == "y" || inputNpwp == "t" ) {
                            npwp = inputNpwp == "y"
                            validInput = true
                        } else {
                            throw Exception("Input pilihan NPWP tidak valid, Pilihan hanya Y dan T. Masukan Ulang")
                        }
                    } catch (e: Exception) {
                        println(e.message)
                    }
                }

                // Membuat objek transaksi beli emas
                val transaksi = TransaksiBeliEmas(beratEmas, npwp)

                // Menghitung dan mencetak harga beli emas dan menko
                val hargaBeli = transaksi.hitungHargaBeli()
                val formatHargaBeli = hargaBeli.toInt() // mengubah tipe data sebelumnya ke Int
                println("Harga beli emas: Rp $formatHargaBeli") // Menampilkan tipe data yang sudah diubah ke Int
            }
            else -> {
                println("Pilihan tidak valid")
            }
        }
    } catch (e: Exception) {
        println("Terjadi kesalahan: ${e.message}")
    }
}
