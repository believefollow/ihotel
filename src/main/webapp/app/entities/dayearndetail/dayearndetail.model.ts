import * as dayjs from 'dayjs';

export interface IDayearndetail {
  id?: number;
  earndate?: dayjs.Dayjs;
  salespotn?: number;
  money?: number | null;
}

export class Dayearndetail implements IDayearndetail {
  constructor(public id?: number, public earndate?: dayjs.Dayjs, public salespotn?: number, public money?: number | null) {}
}

export function getDayearndetailIdentifier(dayearndetail: IDayearndetail): number | undefined {
  return dayearndetail.id;
}
