jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { FwDsService } from '../service/fw-ds.service';
import { IFwDs, FwDs } from '../fw-ds.model';

import { FwDsUpdateComponent } from './fw-ds-update.component';

describe('Component Tests', () => {
  describe('FwDs Management Update Component', () => {
    let comp: FwDsUpdateComponent;
    let fixture: ComponentFixture<FwDsUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let fwDsService: FwDsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [FwDsUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(FwDsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FwDsUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      fwDsService = TestBed.inject(FwDsService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const fwDs: IFwDs = { id: 456 };

        activatedRoute.data = of({ fwDs });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(fwDs));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const fwDs = { id: 123 };
        spyOn(fwDsService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ fwDs });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: fwDs }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(fwDsService.update).toHaveBeenCalledWith(fwDs);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const fwDs = new FwDs();
        spyOn(fwDsService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ fwDs });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: fwDs }));
        saveSubject.complete();

        // THEN
        expect(fwDsService.create).toHaveBeenCalledWith(fwDs);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const fwDs = { id: 123 };
        spyOn(fwDsService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ fwDs });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(fwDsService.update).toHaveBeenCalledWith(fwDs);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
