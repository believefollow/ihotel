import * as dayjs from 'dayjs';

export interface IClassRename {
  id?: number;
  dt?: dayjs.Dayjs;
  empn?: string;
  oldmoney?: number | null;
  getmoney?: number | null;
  toup?: number | null;
  downempn?: string | null;
  todown?: number | null;
  flag?: number | null;
  old2?: number | null;
  get2?: number | null;
  toup2?: number | null;
  todown2?: number | null;
  upempn2?: string | null;
  im9008?: number | null;
  im9009?: number | null;
  co9991?: number | null;
  co9992?: number | null;
  co9993?: number | null;
  co9994?: number | null;
  co9995?: number | null;
  co9998?: number | null;
  im9007?: number | null;
  gotime?: dayjs.Dayjs | null;
  co9999?: number | null;
  cm9008?: number | null;
  cm9009?: number | null;
  co99910?: number | null;
  checkSign?: string | null;
  classPb?: string | null;
  ck?: number | null;
  dk?: number | null;
  sjrq?: dayjs.Dayjs | null;
  qsjrq?: dayjs.Dayjs | null;
  byje?: number | null;
  xfcw?: string | null;
  hoteldm?: string | null;
  isnew?: number | null;
  co99912?: number | null;
  xj?: number | null;
  classname?: string | null;
  co9010?: number | null;
  co9012?: number | null;
  co9013?: number | null;
  co9014?: number | null;
  co99915?: number | null;
  hyxj?: number | null;
  hysk?: number | null;
  hyqt?: number | null;
  hkxj?: number | null;
  hksk?: number | null;
  hkqt?: number | null;
  qtwt?: number | null;
  qtysq?: number | null;
  bbysj?: number | null;
  zfbje?: number | null;
  wxje?: number | null;
  w99920?: number | null;
  z99921?: number | null;
}

export class ClassRename implements IClassRename {
  constructor(
    public id?: number,
    public dt?: dayjs.Dayjs,
    public empn?: string,
    public oldmoney?: number | null,
    public getmoney?: number | null,
    public toup?: number | null,
    public downempn?: string | null,
    public todown?: number | null,
    public flag?: number | null,
    public old2?: number | null,
    public get2?: number | null,
    public toup2?: number | null,
    public todown2?: number | null,
    public upempn2?: string | null,
    public im9008?: number | null,
    public im9009?: number | null,
    public co9991?: number | null,
    public co9992?: number | null,
    public co9993?: number | null,
    public co9994?: number | null,
    public co9995?: number | null,
    public co9998?: number | null,
    public im9007?: number | null,
    public gotime?: dayjs.Dayjs | null,
    public co9999?: number | null,
    public cm9008?: number | null,
    public cm9009?: number | null,
    public co99910?: number | null,
    public checkSign?: string | null,
    public classPb?: string | null,
    public ck?: number | null,
    public dk?: number | null,
    public sjrq?: dayjs.Dayjs | null,
    public qsjrq?: dayjs.Dayjs | null,
    public byje?: number | null,
    public xfcw?: string | null,
    public hoteldm?: string | null,
    public isnew?: number | null,
    public co99912?: number | null,
    public xj?: number | null,
    public classname?: string | null,
    public co9010?: number | null,
    public co9012?: number | null,
    public co9013?: number | null,
    public co9014?: number | null,
    public co99915?: number | null,
    public hyxj?: number | null,
    public hysk?: number | null,
    public hyqt?: number | null,
    public hkxj?: number | null,
    public hksk?: number | null,
    public hkqt?: number | null,
    public qtwt?: number | null,
    public qtysq?: number | null,
    public bbysj?: number | null,
    public zfbje?: number | null,
    public wxje?: number | null,
    public w99920?: number | null,
    public z99921?: number | null
  ) {}
}

export function getClassRenameIdentifier(classRename: IClassRename): number | undefined {
  return classRename.id;
}
