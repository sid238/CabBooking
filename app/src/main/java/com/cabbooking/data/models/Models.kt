package com.cabbooking.data.models

data class User(
    val name: String = "", val email: String = "", val phone: String = "", val photo: String = ""
)

data class RideType(
    val id: Int, val name: String, val icon: Int, val description: String,
    val baseFare: Double, val perKm: Double, val perMin: Double, val seats: Int, val eta: String
)

data class Location(
    val lat: Double = 0.0, val lng: Double = 0.0, val address: String = "", val shortName: String = ""
)

data class FareBreakdown(
    val baseFare: Double, val distance: Double, val duration: String,
    val distanceFare: Double, val timeFare: Double, val surge: Double = 1.0, val total: Double
)

data class Driver(
    val id: String = "", val name: String = "", val phone: String = "",
    val photo: String = "", val rating: Double = 4.8, val vehicle: String = "",
    val plate: String = "", val lat: Double = 0.0, val lng: Double = 0.0
)

enum class RideStatus { REQUESTED, ACCEPTED, ARRIVED, STARTED, COMPLETED, CANCELLED }

data class Ride(
    val id: String = "", val riderId: String = "", val driver: Driver = Driver(),
    val pickup: Location = Location(), val dropoff: Location = Location(),
    val type: RideType? = null, val status: RideStatus = RideStatus.REQUESTED,
    val fare: Double = 0.0, val distance: Double = 0.0, val duration: String = "",
    val otp: String = "1234", val date: String = ""
)

data class Parcel(
    val id: String = "", val senderName: String = "", val senderPhone: String = "",
    val receiverName: String = "", val receiverPhone: String = "",
    val pickup: Location = Location(), val dropoff: Location = Location(),
    val size: String = "Medium", val weight: String = "2 kg",
    val fare: Double = 0.0, val status: String = "Booked", val date: String = ""
)

data class WalletTransaction(
    val id: String, val type: String, val amount: Double, val description: String, val date: String
)

object SampleData {
    val rideTypes = listOf(
        RideType(1, "Cab", 0, "Sedan · 4 seats", 50.0, 12.0, 2.0, 4, "3 min"),
        RideType(2, "Bike", 0, "Fast · 1 seat", 20.0, 6.0, 1.0, 1, "2 min"),
        RideType(3, "Auto", 0, "Auto · 3 seats", 30.0, 8.0, 1.5, 3, "4 min"),
        RideType(4, "Parcel", 0, "Delivery", 25.0, 5.0, 0.0, 0, "5 min")
    )

    val recentRides = listOf(
        Ride("1", "", Driver("", "Rajesh K", "", "", 4.8, "Swift", "KA01AB1234"),
            Location(0.0, 0.0, "MG Road, Bangalore"), Location(0.0, 0.0, "Koramangala, Bangalore"),
            rideTypes[0], RideStatus.COMPLETED, 250.0, 8.5, "25 min", date = "Today, 2:30 PM"),
        Ride("2", "", Driver("", "Suresh P", "", "", 4.6, "Access 125", "KA02CD5678"),
            Location(0.0, 0.0, "Indiranagar, Bangalore"), Location(0.0, 0.0, "Whitefield, Bangalore"),
            rideTypes[1], RideStatus.COMPLETED, 120.0, 5.2, "15 min", date = "Yesterday, 6:15 PM")
    )

    val savedPlaces = listOf(
        Location(0.0, 0.0, "123 MG Road, Bangalore", "Home"),
        Location(0.0, 0.0, "456 Koramangala, Bangalore", "Work")
    )

    val transactions = listOf(
        WalletTransaction("1", "credit", 500.0, "Added to wallet", "Today"),
        WalletTransaction("2", "debit", 250.0, "Ride payment", "Today"),
        WalletTransaction("3", "credit", 1000.0, "Added to wallet", "Yesterday")
    )
}
