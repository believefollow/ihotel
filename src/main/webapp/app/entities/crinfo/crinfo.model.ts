import * as dayjs from 'dayjs';

export interface ICrinfo {
  id?: number;
  operatetime?: dayjs.Dayjs | null;
  oldrent?: number | null;
  newrent?: number | null;
  oldroomn?: string | null;
  newroomn?: string | null;
  account?: string | null;
  empn?: string | null;
  oldday?: number | null;
  newday?: number | null;
  hoteltime?: dayjs.Dayjs | null;
  roomn?: string | null;
  memo?: string;
  realname?: string | null;
  isup?: number | null;
}

export class Crinfo implements ICrinfo {
  constructor(
    public id?: number,
    public operatetime?: dayjs.Dayjs | null,
    public oldrent?: number | null,
    public newrent?: number | null,
    public oldroomn?: string | null,
    public newroomn?: string | null,
    public account?: string | null,
    public empn?: string | null,
    public oldday?: number | null,
    public newday?: number | null,
    public hoteltime?: dayjs.Dayjs | null,
    public roomn?: string | null,
    public memo?: string,
    public realname?: string | null,
    public isup?: number | null
  ) {}
}

export function getCrinfoIdentifier(crinfo: ICrinfo): number | undefined {
  return crinfo.id;
}
