import * as dayjs from 'dayjs';

export interface IClog {
  id?: number;
  empn?: string | null;
  begindate?: dayjs.Dayjs | null;
  enddate?: dayjs.Dayjs | null;
  dqrq?: dayjs.Dayjs | null;
}

export class Clog implements IClog {
  constructor(
    public id?: number,
    public empn?: string | null,
    public begindate?: dayjs.Dayjs | null,
    public enddate?: dayjs.Dayjs | null,
    public dqrq?: dayjs.Dayjs | null
  ) {}
}

export function getClogIdentifier(clog: IClog): number | undefined {
  return clog.id;
}
