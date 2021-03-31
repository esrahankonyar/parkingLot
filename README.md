# parkingLot

1) Create parking lot request example:
  {
  "capacity": 4,
  "city": "Ankara",
  "name": "Ankara oto",
  "priceList": {
    "priceListDetails": [
      {
        "hour": "0-2",
        "price": 10
      },
      {
        "hour": "2-4",
        "price": 12
      },
      {
        "hour": "4-8",
        "price": 13
      },
      {
        "hour": "8-14",
        "price": 16
      },
      {
        "hour": "14-24",
        "price": 20
      }
    ]
  }
}
2) Example request for getParkingAreaByName method:
  name = Ankara oto
3) Example request for updateParkingArea method:
  {
  "id": 1,
  "name": "Ankara oto",
  "capacity": 3,
  "city": "Ankara",
  "priceList": {
    "id": 1,
    "priceListDetails": [
      {
        "hour": "0-2",
        "price": 10
      },
      {
        "hour": "2-4",
        "price": 12
      },
      {
        "hour": "4-8",
        "price": 13
      },
      {
        "hour": "8-14",
        "price": 16
      },
      {
        "hour": "14-24",
        "price": 20
      }
    ]
  }
}
4) Example request for checkIn methods:
  {
  "checkInDate": "2021-03-31T20:02:24.331Z",
  "parkingArea": {
    "id": 1
  },
  "vehicle": {
    "licensePlate": "34 IST 34"
  }
}
5) Example for request checkOut methods:
  {
  "licensePlate": "34 IST 34"
}
6) Example request for getParkingDetails methods:
  
