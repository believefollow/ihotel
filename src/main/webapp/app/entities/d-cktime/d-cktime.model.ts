import * as dayjs from 'dayjs';

export interface IDCktime {
  id?: number;
  begintime?: dayjs.Dayjs;
  endtime?: dayjs.Dayjs;
  depot?: string;
  ckbillno?: string | null;
}

export class DCktime implements IDCktime {
  constructor(
    public id?: number,
    public begintime?: dayjs.Dayjs,
    public endtime?: dayjs.Dayjs,
    public depot?: string,
    public ckbillno?: string | null
  ) {}
}

export function getDCktimeIdentifier(dCktime: IDCktime): number | undefined {
  return dCktime.id;
}
