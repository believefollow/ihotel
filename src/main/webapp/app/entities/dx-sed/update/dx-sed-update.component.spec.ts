jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DxSedService } from '../service/dx-sed.service';
import { IDxSed, DxSed } from '../dx-sed.model';

import { DxSedUpdateComponent } from './dx-sed-update.component';

describe('Component Tests', () => {
  describe('DxSed Management Update Component', () => {
    let comp: DxSedUpdateComponent;
    let fixture: ComponentFixture<DxSedUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let dxSedService: DxSedService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DxSedUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DxSedUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DxSedUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      dxSedService = TestBed.inject(DxSedService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const dxSed: IDxSed = { id: 456 };

        activatedRoute.data = of({ dxSed });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(dxSed));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dxSed = { id: 123 };
        spyOn(dxSedService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dxSed });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dxSed }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(dxSedService.update).toHaveBeenCalledWith(dxSed);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dxSed = new DxSed();
        spyOn(dxSedService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dxSed });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: dxSed }));
        saveSubject.complete();

        // THEN
        expect(dxSedService.create).toHaveBeenCalledWith(dxSed);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const dxSed = { id: 123 };
        spyOn(dxSedService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ dxSed });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(dxSedService.update).toHaveBeenCalledWith(dxSed);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
