import { IBookings } from 'app/shared/model/bookings.model';
import { ITrain } from 'app/shared/model/train.model';
import { IBus } from 'app/shared/model/bus.model';
import { IAirplane } from 'app/shared/model/airplane.model';
import { ISource } from 'app/shared/model/source.model';
import { IDestination } from 'app/shared/model/destination.model';

export const enum TransportType {
  Roadways = 'Roadways',
  Railways = 'Railways',
  Airways = 'Airways'
}

export const enum WeekDays {
  SUNDAY = 'SUNDAY',
  MONDAY = 'MONDAY',
  TUESDAY = 'TUESDAY',
  WEDNESDAY = 'WEDNESDAY',
  THURSDAY = 'THURSDAY',
  FRIDAY = 'FRIDAY',
  SATURDAY = 'SATURDAY'
}

export interface ITransport {
  id?: number;
  transportType?: TransportType;
  serviceProviderName?: string;
  availability?: WeekDays;
  transports?: IBookings[];
  train?: ITrain;
  bus?: IBus;
  airplane?: IAirplane;
  source?: ISource;
  destination?: IDestination;
}

export class Transport implements ITransport {
  constructor(
    public id?: number,
    public transportType?: TransportType,
    public serviceProviderName?: string,
    public availability?: WeekDays,
    public transports?: IBookings[],
    public train?: ITrain,
    public bus?: IBus,
    public airplane?: IAirplane,
    public source?: ISource,
    public destination?: IDestination
  ) {}
}
