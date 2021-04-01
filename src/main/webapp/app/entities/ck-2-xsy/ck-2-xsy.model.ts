import * as dayjs from 'dayjs';

export interface ICk2xsy {
  id?: number;
  rq?: dayjs.Dayjs;
  cpbh?: string;
  sl?: number;
}

export class Ck2xsy implements ICk2xsy {
  constructor(public id?: number, public rq?: dayjs.Dayjs, public cpbh?: string, public sl?: number) {}
}

export function getCk2xsyIdentifier(ck2xsy: ICk2xsy): number | undefined {
  return ck2xsy.id;
}
