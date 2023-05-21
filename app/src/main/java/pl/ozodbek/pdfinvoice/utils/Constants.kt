package pl.ozodbek.pdfinvoice.utils

class Constants {

    companion object{

        const val MINIMUM_SENDING_AMOUNT = 18.29
        const val MAXIMUM_SENDING_AMOUNT = 22710
        const val MINIMUM_RECEIVING_AMOUNT = 49214.13
        const val MAXIMUM_RECEIVING_AMOUNT = 6109849.78

        const val MINIMUM_SENDING_AMOUNT_ERROR_MESSAGE = "Summa 18.29 zl dan kam\nbo'lmasligi kerak"
        const val MAXIMUM_SENDING_AMOUNT_ERROR_MESSAGE = "Summa 22 710,02 zl dan ko'p bo'lmasligi kerak"
        const val MINIMUM_RECEIVING_AMOUNT_ERROR_MESSAGE = "Summa 49 214,13 UZS dan\nkam bo'lmasligi kerak"
        const val MAXIMUM_RECEIVING_AMOUNT_ERROR_MESSAGE = "Summa 61 098487,78 UZS dan ko'p bo'lmasligi kerak"
        const val NO_AMOUNT_ERROR_MESSAGE = "Summani kiriting"

    }

}