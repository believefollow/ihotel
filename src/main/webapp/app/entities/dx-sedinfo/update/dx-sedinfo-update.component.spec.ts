jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DxSedinfoService } from '../service/dx-sedinfo.service';
import { IDxSedinfo, DxSedinfo } from '../dx-sedinfo.model';

import { DxSedinfoUpdateComponent } from './dx-sedinfo-update.component';

describe('Component Tests', () => {
  describe('DxSedinfo Management Update Component', () => {
    let comp: DxSedinfoUpdateComponent;
    let fixture: ComponentFixture<DxSedinfoUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let dxSedinfoService: DxSedinfoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DxSedinfoUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DxSedinfoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DxSedinfoUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      dxSedinfoService = TestBed.inject(DxSedinfoService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const dxSedinfo: IDxSedinfo = { id: 456 };

        activatedRoute.data = of({ dxSedinfo });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(dxSedinfo));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dxSedinfo = { id: 123 };
        spyOn(dxSedinfoService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dxSedinfo });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dxSedinfo }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(dxSedinfoService.update).toHaveBeenCalledWith(dxSedinfo);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dxSedinfo = new DxSedinfo();
        spyOn(dxSedinfoService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dxSedinfo });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dxSedinfo }));
        saveSubject.complete();

        // THEN
        expect(dxSedinfoService.create).toHaveBeenCalledWith(dxSedinfo);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dxSedinfo = { id: 123 };
        spyOn(dxSedinfoService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dxSedinfo });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(dxSedinfoService.update).toHaveBeenCalledWith(dxSedinfo);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
