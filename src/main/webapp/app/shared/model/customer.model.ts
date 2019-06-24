import { IBookings } from 'app/shared/model/bookings.model';

export const enum Gender {
  Male = 'Male',
  Female = 'Female',
  Others = 'Others'
}

export interface ICustomer {
  id?: number;
  cName?: string;
  cAge?: number;
  cGender?: Gender;
  phone?: string;
  email?: string;
  customers?: IBookings[];
}

export class Customer implements ICustomer {
  constructor(
    public id?: number,
    public cName?: string,
    public cAge?: number,
    public cGender?: Gender,
    public phone?: string,
    public email?: string,
    public customers?: IBookings[]
  ) {}
}
