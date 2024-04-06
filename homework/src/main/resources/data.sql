-- Insert Stops
INSERT INTO stop (city_name, arrival_time, departure_time) VALUES ('Porto', '5', '6');
INSERT INTO stop (city_name, arrival_time, departure_time) VALUES ('Lisboa', '7', '8');
INSERT INTO stop (city_name, arrival_time, departure_time) VALUES ('Braga', '9', '10');
INSERT INTO stop (city_name, arrival_time, departure_time) VALUES ('Coimbra', '11', '12');
INSERT INTO stop (city_name, arrival_time, departure_time) VALUES ('Faro', '13', '14');

-- Insert Routes
INSERT INTO route (id) VALUES (1);
INSERT INTO route (id) VALUES (2);

-- Insert Seats
INSERT INTO seat (seat_number, price_multiplier, route_id) VALUES (1, 1, 1);
INSERT INTO seat (seat_number, price_multiplier, route_id) VALUES (2, 1, 1);
INSERT INTO seat (seat_number, price_multiplier, route_id) VALUES (3, 1, 2);
INSERT INTO seat (seat_number, price_multiplier, route_id) VALUES (4, 1, 2);

-- Insert Reservations
INSERT INTO reservation (client_name, price, route_id, dep_stop_id, arr_stop_id, confirmation_code)
VALUES ('John Doe', 50, 1, 1, 2, 12345);
INSERT INTO reservation (client_name, price, route_id, dep_stop_id, arr_stop_id, confirmation_code)
VALUES ('Jane Smith', 60, 2, 3, 4, 54321);

-- Insert associations between Seats and Reservations
INSERT INTO seat_reservation (seat_id, reservation_id) VALUES (1, 1);
INSERT INTO seat_reservation (seat_id, reservation_id) VALUES (2, 1);
INSERT INTO seat_reservation (seat_id, reservation_id) VALUES (3, 2);
INSERT INTO seat_reservation (seat_id, reservation_id) VALUES (4, 2);

-- Insert associations between Stops and Routes
INSERT INTO stop_route (stop_id, route_id) VALUES (1, 1);
INSERT INTO stop_route (stop_id, route_id) VALUES (2, 1);
INSERT INTO stop_route (stop_id, route_id) VALUES (3, 2);
INSERT INTO stop_route (stop_id, route_id) VALUES (4, 2);
