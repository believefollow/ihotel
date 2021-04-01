import * as dayjs from 'dayjs';

export interface IClassreport {
  id?: number;
  empn?: string;
  dt?: dayjs.Dayjs;
  xjUp?: number | null;
  yfjA?: number | null;
  yfjD?: number | null;
  gz?: number | null;
  zz?: number | null;
  zzYj?: number | null;
  zzJs?: number | null;
  zzTc?: number | null;
  ff?: number | null;
  minibar?: number | null;
  phone?: number | null;
  other?: number | null;
  pc?: number | null;
  cz?: number | null;
  cy?: number | null;
  md?: number | null;
  huiy?: number | null;
  dtb?: number | null;
  sszx?: number | null;
  cyz?: number | null;
  hoteldm?: string | null;
  gzxj?: number | null;
  isnew?: number | null;
}

export class Classreport implements IClassreport {
  constructor(
    public id?: number,
    public empn?: string,
    public dt?: dayjs.Dayjs,
    public xjUp?: number | null,
    public yfjA?: number | null,
    public yfjD?: number | null,
    public gz?: number | null,
    public zz?: number | null,
    public zzYj?: number | null,
    public zzJs?: number | null,
    public zzTc?: number | null,
    public ff?: number | null,
    public minibar?: number | null,
    public phone?: number | null,
    public other?: number | null,
    public pc?: number | null,
    public cz?: number | null,
    public cy?: number | null,
    public md?: number | null,
    public huiy?: number | null,
    public dtb?: number | null,
    public sszx?: number | null,
    public cyz?: number | null,
    public hoteldm?: string | null,
    public gzxj?: number | null,
    public isnew?: number | null
  ) {}
}

export function getClassreportIdentifier(classreport: IClassreport): number | undefined {
  return classreport.id;
}
