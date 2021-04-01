import * as dayjs from 'dayjs';

export interface IBookingtime {
  id?: number;
  bookid?: string | null;
  roomn?: string | null;
  booktime?: dayjs.Dayjs | null;
  rtype?: string | null;
  sl?: number | null;
  remark?: string | null;
  sign?: number | null;
  rzsign?: number | null;
}

export class Bookingtime implements IBookingtime {
  constructor(
    public id?: number,
    public bookid?: string | null,
    public roomn?: string | null,
    public booktime?: dayjs.Dayjs | null,
    public rtype?: string | null,
    public sl?: number | null,
    public remark?: string | null,
    public sign?: number | null,
    public rzsign?: number | null
  ) {}
}

export function getBookingtimeIdentifier(bookingtime: IBookingtime): number | undefined {
  return bookingtime.id;
}
