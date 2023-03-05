package edu.kit.kastel.trafficsimulation.entity;

import edu.kit.kastel.trafficsimulation.utility.Position;

public class Car {

    private final int id;
    private final Position position;
    private int speed = 0;
    private final int maxSpeed;
    private final int acceleration;
    private int turnPreference = 0;

    public Car(int id, Position position, int maxSpeed, int acceleration) {
        this.id = id;
        this.position = position;
        this.maxSpeed = maxSpeed;
        this.acceleration = acceleration;
    }

    public void simulate() {
        Position position = this.getPosition();
        Street street = position.getStreet();
        this.speed = Math.min(Math.min(maxSpeed, this.speed + acceleration), street.getMaximumSpeed());
        int possibleDriveDistance = this.speed;

        int leftDistanceOnStreet = this.getLeftDistanceOnStreet(street, this.position);

        int actualDriveDistanceOnStreet = 0;
        if (leftDistanceOnStreet != 0) {
            int possibleDriveOnStreet = Math.min(leftDistanceOnStreet, possibleDriveDistance);
            actualDriveDistanceOnStreet = this.getPosition().getStreet()
                .calculateDrivingDistance(this, possibleDriveOnStreet);
            if (actualDriveDistanceOnStreet != 0) {
                this.drive(actualDriveDistanceOnStreet);
            }
        }
        leftDistanceOnStreet = this.getLeftDistanceOnStreet(street, this.position);
        int leftPossibleDriveDistance = possibleDriveDistance - actualDriveDistanceOnStreet;
        if (leftDistanceOnStreet == 0 && leftPossibleDriveDistance > 0 && !street.didCarOvertake(this)) {
            Street crossingStreet = street.endCrossing.cross(this, this.turnPreference);
            if (crossingStreet != null) {
                int possibleDriveOnStreet = Math.min(leftPossibleDriveDistance, crossingStreet.getLength());
                actualDriveDistanceOnStreet = crossingStreet.calculateGetOnStreetDistance(possibleDriveOnStreet);
                if (actualDriveDistanceOnStreet >= 0) {
                    this.crossStreetTo(crossingStreet);
                    this.drive(actualDriveDistanceOnStreet);
                    this.changeTurnPreference();
                }
            }
        }

        if (actualDriveDistanceOnStreet == 0) {
            this.stop();
        }



    }

    public int getId() {
        return id;
    }

    public int getSpeed() {
        return speed;
    }

    public Position getPosition() {
        return this.position;
    }

    private void changeTurnPreference() {
        this.turnPreference += 1;
        if (turnPreference > Crossing.MAX_ALLOWED_STREETS) {
            this.turnPreference = 0;
        }
    }

    private int getLeftDistanceOnStreet(Street street, Position position) {
        return street.getLength() - position.getMileage();
    }

    private void stop() {
        this.speed = 0;
    }

    private void drive(int meters) {
        this.position.setMileage(this.position.getMileage() + meters);
    }

    private void crossStreetTo(Street street) {
        this.position.setMileage(0);
        this.position.setStreet(street);
    }
}
